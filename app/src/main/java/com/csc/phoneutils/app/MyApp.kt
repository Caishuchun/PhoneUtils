package com.csc.phoneutils.app

import android.app.Application
import androidx.room.Room
import com.csc.phoneutils.data.UserDataBase
import kotlin.properties.Delegates


/**
 * Author: 蔡小树
 * Time: 2019/11/15 14:26
 * Description:
 */
class MyApp : Application() {
    val TAG = javaClass.name

    companion object {
        var instance: MyApp by Delegates.notNull()
        var userDB: UserDataBase by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        userDB =
            Room.databaseBuilder(this, UserDataBase::class.java, "db_users").addMigrations().build()
    }
}