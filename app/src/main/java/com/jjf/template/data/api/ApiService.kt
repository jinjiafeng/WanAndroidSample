package com.jjf.template.data.api

import androidx.lifecycle.LiveData
import com.jjf.template.result.ArticleList
import com.jjf.template.result.ProjectCategory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author jjf
 * date: 18-11-7
 * description :
 */
interface ApiService {

    /**
     * 项目分类
     * @param
     * @return 项目分类
     */
    @GET("project/tree/json")
    fun homeCategory(): LiveData<ApiResponse<ProjectCategory>>


    /**
     * 某一个分类下项目列表数据，分页展示
     * @param
     * @return 项目详情
     */
    @GET("project/list/{page}/json")
    fun homeArticleList(@Path("page") page: Int,@Query("cid")cid:Int): LiveData<ApiResponse<ArticleList>>

}
