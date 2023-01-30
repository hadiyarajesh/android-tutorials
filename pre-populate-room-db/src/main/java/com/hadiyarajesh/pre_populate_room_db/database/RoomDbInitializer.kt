package com.hadiyarajesh.pre_populate_room_db.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hadiyarajesh.pre_populate_room_db.database.dao.UserDao
import com.hadiyarajesh.pre_populate_room_db.database.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Provider
import kotlin.random.Random

/**
 * This class implements [RoomDatabase.Callback] and override [RoomDatabase.Callback.onCreate] method to initialise room database when it's created for the first time.
 * Using [Provider] of [UserDao] to break the circular dependency
 */
class RoomDbInitializer(
    private val userProvider: Provider<UserDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        populateUsers()
    }

    private suspend fun populateUsers() {
        userProvider.get().insertOrUpdateUsers(*userGenerator.take(100).toList().toTypedArray())
    }
}

/**
 * This is a [Sequence] generator to generate random users.
 */
val userGenerator = generateSequence {
    User(
        userId = 0,
        username = "user_${UUID.randomUUID().toString().replace("-", "").substring(0, 6)}",
        isActive = Random.nextBoolean()
    )
}
