package com.csc.phoneutils.data

import androidx.room.*

/**
 * Author: 蔡小树
 * Time: 2019/11/15 15:48
 * Description:
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM tb_users")
    fun getInfoes(): List<UserInfo>?

    @Query("SELECT * FROM tb_users WHERE tel =:phone")
    fun getInfo(phone: String): UserInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userInfo: UserInfo): Long

    @Update
    fun updateUser(vararg userInfo: UserInfo)

    @Query("DELETE FROM tb_users WHERE tel =:phone")
    fun deleteUser(phone: String): Int

    @Query("DELETE FROM tb_users")
    fun deleteAll()

}