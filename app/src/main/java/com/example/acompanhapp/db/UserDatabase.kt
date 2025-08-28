package com.example.acompanhapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.acompanhapp.dao.UserDao
import com.example.acompanhapp.model.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
