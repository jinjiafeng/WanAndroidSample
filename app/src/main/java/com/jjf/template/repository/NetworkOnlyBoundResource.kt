package com.jjf.template.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jjf.template.AppExecutors
import com.jjf.template.api.ApiResponse
import com.jjf.template.bean.Resource

/**
 * @author jinjiafeng
 * Time :18-11-10
 * description: 不需要储存数据库，只需要网络请求的情况
 */
abstract class NetworkOnlyBoundResource<RequestType>(appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        @Suppress("LeakingThis") val apiResponse = createCall()
        result.value = Resource.loading(null)
        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiResponse.ApiSuccessResponse<*> -> appExecutors.mainThread().execute { result.setValue(Resource.success((response as ApiResponse.ApiSuccessResponse<RequestType>).body)) }
                is ApiResponse.ApiEmptyResponse<*> -> appExecutors.mainThread().execute { result.setValue(Resource.success(null)) }
                is ApiResponse.ApiErrorResponse<*> -> appExecutors.mainThread().execute {
                    val errorMessage = (response as ApiResponse.ApiErrorResponse<RequestType>).errorMessage
                    result.setValue(Resource.error(errorMessage, null))
                    onFetchFailed()
                }
            }
        }
    }


    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    fun asLiveData(): LiveData<Resource<RequestType>>  = result

    protected fun onFetchFailed() {}

}
