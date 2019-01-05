package com.jjf.template.data.api

import androidx.lifecycle.LiveData
import com.jjf.template.result.User
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author jjf
 * date: 18-11-7
 * description :
 */
interface GithubService {
    /**
     * 登录
     * @param login
     * @return 用户数据
     */
    @GET("users/{login}")
    fun getUser(@Path("login") login: String): LiveData<ApiResponse<User>>

}
