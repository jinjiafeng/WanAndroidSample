package com.jjf.template.repository

import androidx.lifecycle.LiveData
import com.jjf.template.AppExecutors
import com.jjf.template.data.api.ApiResponse
import com.jjf.template.data.api.ApiService
import com.jjf.template.data.db.ArticleDao
import com.jjf.template.result.Article
import com.jjf.template.result.ArticleList
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
constructor(private val executors: AppExecutors, private val service: ApiService, private val articleDao: ArticleDao) {

    private val repoListRateLimit = RateLimiter<Int>(10, TimeUnit.MINUTES)

    fun loadHomeArticle(page: Int): LiveData<Resource<List<Article>>> {
        return object : NetworkBoundResource<List<Article>, ArticleList>(executors) {

            override fun loadFromDb(): LiveData<List<Article>> {
                return articleDao.loadHomeArticle()
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(page)
            }

            override fun createCall(): LiveData<ApiResponse<ArticleList>> =
                    service.homeArticleList(page)

            override fun saveCallResult(item: ArticleList) {
                articleDao.insert(item.data.datas)
            }
        }.asLiveData()
    }
}
