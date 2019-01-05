package com.jjf.template.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjf.template.result.Resource
import com.jjf.template.result.User
import com.jjf.template.repository.UserRepository
import com.jjf.template.util.lifecycle.AbsentLiveData
import com.jjf.template.util.switchMap
import javax.inject.Inject

/**
 * @author xj
 * date: 18-11-8
 * description :
 */

class UserViewModel
@Inject constructor(private val mUserRepository: UserRepository) : ViewModel() {
    private val loginData = MutableLiveData<String>()
    val user:LiveData<Resource<User>> = loginData.switchMap { login->
        if(login == null){
            AbsentLiveData.create()
        }else{
            mUserRepository.loadUser(login)
        }
    }
    fun setLogin(login: String) {
        if (login != loginData.value) {
            loginData.value = login
        }
    }
}
