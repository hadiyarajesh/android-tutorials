package com.hadiyarajesh.pre_populate_room_db.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * In SQL, columns can have null values by default and need to be explicitly marked as non null if you want otherwise.
 * This is the opposite of how things work in Kotlin, where values can't be null by default.
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @NonNull val username: String,
    @NonNull val isActive: Boolean,
)
