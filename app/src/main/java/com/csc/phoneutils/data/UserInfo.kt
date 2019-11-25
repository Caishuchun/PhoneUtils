package com.csc.phoneutils.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: 蔡小树
 * Time: 2019/11/15 15:44
 * Description:
 */

@Entity(tableName = "tb_users")
data class UserInfo(
    @PrimaryKey val tel: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "picture") val picture: String?,
    @ColumnInfo(name = "address") val address: String?
)