package com.jjf.template.repository

import androidx.lifecycle.LiveData
import com.jjf.template.AppExecutors
import com.jjf.template.data.api.ApiResponse
import com.jjf.template.data.api.GithubService
import com.jjf.template.result.Resource
import com.jjf.template.result.User
import com.jjf.template.data.db.UserDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
@Singleton
class UserRepository @Inject
constructor(private val mAppExecutors: AppExecutors, private val mService: GithubService, private val userDao: UserDao) {

    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(mAppExecutors) {
            override fun shouldFetch(data: User?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao.findByLogin(login)
            }

            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun createCall(): LiveData<ApiResponse<User>> {
                return mService.getUser(login)
            }
        }.asLiveData()
    }
}
