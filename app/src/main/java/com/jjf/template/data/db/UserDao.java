package com.jjf.template.data.db;

import com.jjf.template.result.User;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM user WHERE login = :login ")
    LiveData<User> findByLogin(String login);
}
