package com.jjf.template.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jjf.template.AppExecutors
import com.jjf.template.data.api.ApiEmptyResponse
import com.jjf.template.data.api.ApiErrorResponse
import com.jjf.template.data.api.ApiResponse
import com.jjf.template.data.api.ApiSuccessResponse
import com.jjf.template.result.Resource

/**
 * @author jjf
 * date: 18-11-7
 * description : A generic class that can provide a resource backed by both the sqlite database and the
 * network.
 * * @param <ResultType> 存储到数据库的类型
 * * @param <RequestType> 网络请求获取的类型
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>(private val mAppExecutors: AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis") val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    @MainThread
    abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * 是否需要请求网络
     *
     * @param data
     * @return
     */
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> setValue(Resource.loading(newData)) }

        result.addSource(apiResponse) { response ->
            result.removeSource(dbSource)
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse<*> -> mAppExecutors.diskIO().execute {
                    saveCallResult(processResponse(response as ApiSuccessResponse<RequestType>))
                    mAppExecutors.mainThread().execute {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb()) { newData -> setValue(Resource.success(newData)) }
                    }
                }
                is ApiEmptyResponse<*> -> mAppExecutors.mainThread().execute { result.addSource(loadFromDb()) { newData -> setValue(Resource.success(newData)) } }
                is ApiErrorResponse<*> -> result.addSource(dbSource) { newData ->
                    setValue(Resource.error((response as ApiErrorResponse<RequestType>)
                            .errorMessage, newData))
                }
            }
        }
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value !== newValue) {
            result.value = newValue
        }
    }

    /**
     * 保存到数据库中，如果类型不一致，可以将RequestType->ResultType 保存
     * @param item
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * 处理请求
     * @param response
     * @return
     */
    @WorkerThread
    protected fun processResponse(response: ApiSuccessResponse<RequestType>): RequestType {
        return response.body
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    protected fun onFetchFailed() {}

}
