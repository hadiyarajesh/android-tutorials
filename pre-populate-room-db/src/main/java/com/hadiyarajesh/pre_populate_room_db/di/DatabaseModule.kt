package com.hadiyarajesh.pre_populate_room_db.di

import android.content.Context
import androidx.room.Room
import com.hadiyarajesh.pre_populate_room_db.R
import com.hadiyarajesh.pre_populate_room_db.database.AppDatabase
import com.hadiyarajesh.pre_populate_room_db.database.RoomDbInitializer
import com.hadiyarajesh.pre_populate_room_db.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        userProvider: Provider<UserDao>,
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "${context.getString(R.string.app_name)}.db"
        ).addCallback(
            /**
             * Attach [RoomDbInitializer] as callback to the database
             */
            RoomDbInitializer(userProvider = userProvider)
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao = db.getUserDao()
}
