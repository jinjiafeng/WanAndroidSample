package com.jjf.template.repository

import androidx.lifecycle.LiveData
import com.jjf.template.AppExecutors
import com.jjf.template.data.api.ApiResponse
import com.jjf.template.data.api.ApiService
import com.jjf.template.data.db.ArticleDao
import com.jjf.template.result.ArticleList
import com.jjf.template.result.Category
import com.jjf.template.result.ProjectCategory
import com.jjf.template.result.Resource
import com.jjf.template.util.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
class HomeArticleRepository @Inject
constructor(private val executors: AppExecutors,
            private val service: ApiService,
            private val articleDao: ArticleDao
) {
    private val repoListRateLimit = RateLimiter<Int>(10, TimeUnit.MINUTES)

    fun loadHomeArticle(page: Int,cid:Int): LiveData<Resource<ArticleList>> {
//        return object : NetworkBoundResource<List<Article>, ArticleList>(executors) {
//
//            override fun loadFromDb(): LiveData<List<Article>> {
//                return articleDao.loadHomeArticle(cid)
//            }
//
//            override fun shouldFetch(data: List<Article>?): Boolean {
//                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(cid+100*page)
//            }
//
//            override fun createCall(): LiveData<ApiResponse<ArticleList>> =
//                    service.homeArticleList(page,cid)
//
//            override fun saveCallResult(item: ArticleList) {
//                articleDao.insertProject(item.data.datas)
//            }
//        }.asLiveData()
        return object :NetworkOnlyBoundResource<ArticleList>(executors){
            override fun createCall(): LiveData<ApiResponse<ArticleList>>{
                return  service.homeArticleList(page, cid)
            }
        }.asLiveData()
    }


    fun loadHomeCategory():LiveData<Resource<List<Category>>>{
        return object : NetworkBoundResource<List<Category>, ProjectCategory>(executors) {

            override fun loadFromDb(): LiveData<List<Category>> {
                return articleDao.loadHomeCategory()
            }

            override fun shouldFetch(data: List<Category>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(0)
            }

            override fun createCall(): LiveData<ApiResponse<ProjectCategory>> =
                    service.homeCategory()

            override fun saveCallResult(item: ProjectCategory) {
                articleDao.insertCategory(item.data)
            }
        }.asLiveData()
    }
}
