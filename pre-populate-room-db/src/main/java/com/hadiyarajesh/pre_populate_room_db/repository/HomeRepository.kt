package com.hadiyarajesh.pre_populate_room_db.repository

import com.hadiyarajesh.pre_populate_room_db.database.dao.UserDao
import com.hadiyarajesh.pre_populate_room_db.database.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getFirstUser(): User? {
        return userDao.getUser()
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
}
