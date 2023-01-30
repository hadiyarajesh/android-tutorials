package com.hadiyarajesh.pre_populate_room_db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hadiyarajesh.pre_populate_room_db.database.dao.UserDao
import com.hadiyarajesh.pre_populate_room_db.database.entity.User

@Database(
    version = 1,
    exportSchema = true,
    entities = [User::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
