package com.hadiyarajesh.pre_populate_room_db.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.hadiyarajesh.pre_populate_room_db.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert(User::class)
    suspend fun insertOrUpdateUsers(vararg users: User)

    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getUser(): User?

    @Query("SELECT * FROM User")
    fun getAllUsers(): Flow<List<User>>
}
