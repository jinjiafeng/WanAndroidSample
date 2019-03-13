package com.jjf.template.data.api

import androidx.lifecycle.LiveData
import com.jjf.template.result.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author jjf
 * date: 18-11-7
 * description :
 */
interface ApiService {
    /**
     * 登录
     * @param login
     * @return 用户数据
     */
    @GET("project/list/{page}/json?cid=294")
    fun homeArticleList(@Path("page") page: Int): LiveData<ApiResponse<ArticleList>>

}
