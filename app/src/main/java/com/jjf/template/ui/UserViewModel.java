package com.jjf.template.ui;

import com.jjf.template.bean.Resource;
import com.jjf.template.bean.User;
import com.jjf.template.repository.UserRepository;
import com.jjf.template.util.lifecycle.AbsentLiveData;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * @author xj
 * date: 18-11-8
 * description :
 */

public class UserViewModel extends ViewModel {

    private  UserRepository mUserRepository;
    private MutableLiveData<String> loginData = new MutableLiveData<>();

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public LiveData<Resource<User>> user = Transformations.switchMap(loginData, login -> {
        if (login == null) {
            return AbsentLiveData.Companion.create();
        }else{
            return mUserRepository.loadUser(login);
        }
    });

    public void setLogin(String login){
        if(!login.equals(loginData.getValue())){
            loginData.setValue(login);
        }
    }
}
