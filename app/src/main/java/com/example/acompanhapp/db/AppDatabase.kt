package com.example.acompanhapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.acompanhapp.dao.UserDao
import com.example.acompanhapp.dao.VisitaDao
import com.example.acompanhapp.model.entity.UserEntity
import com.example.acompanhapp.model.entity.VisitaEntity

@Database(entities = [UserEntity::class, VisitaEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun visitaDao(): VisitaDao
}

