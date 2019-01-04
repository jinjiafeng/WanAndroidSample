package com.jjf.template.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jjf.template.bean.User

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {


    abstract fun userDao(): UserDao

}
