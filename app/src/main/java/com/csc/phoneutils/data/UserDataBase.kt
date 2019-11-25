package com.csc.phoneutils.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Author: 蔡小树
 * Time: 2019/11/15 15:54
 * Description:
 */

@Database(entities = [UserInfo::class], version =1)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao: UserDao
}