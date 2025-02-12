package com.personal.tmdb.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    suspend fun getUser(): UserEntity?

    @Upsert
    suspend fun saveUser(user: UserEntity)

    @Delete
    suspend fun removeUser(user: UserEntity)
}