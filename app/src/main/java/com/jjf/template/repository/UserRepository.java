package com.jjf.template.repository;

import com.jjf.template.AppExecutors;
import com.jjf.template.api.ApiResponse;
import com.jjf.template.api.GithubService;
import com.jjf.template.bean.Resource;
import com.jjf.template.bean.User;
import com.jjf.template.db.UserDao;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
@Singleton
public class UserRepository {

    private final GithubService mService;
    private final AppExecutors mAppExecutors;
    private final UserDao userDao;

    @Inject
    public UserRepository(AppExecutors appExecutors,GithubService service,UserDao userDao) {
        this.mAppExecutors = appExecutors;
        this.mService = service;
        this.userDao = userDao;
    }

    public LiveData<Resource<User>> loadUser(String login){
       return new NetworkBoundResource<User, User>(mAppExecutors) {
           @Override
           public LiveData<User> loadFromDb() {
               return userDao.findByLogin(login);
           }

           @Override
           protected boolean shouldFetch(User data) {
               return data == null;
           }

           @Override
           protected void saveCallResult(User item) {
               userDao.insert(item);
           }

           @Override
           protected LiveData<ApiResponse<User>> createCall() {
               return mService.getUser(login);
           }
       }.asLiveData();
    }
}
