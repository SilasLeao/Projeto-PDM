package com.example.acompanhapp.di

import androidx.room.Room
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.dao.UserDao
import com.example.acompanhapp.db.AppDatabase
import com.example.acompanhapp.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // UserPreferences
    single { UserPreferences(androidContext()) }

    // Retrofit API
    single { RetrofitClient.getClient() }

    // Room Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single { get<AppDatabase>().userDao() }

    // ViewModels
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { EquipeMedicaViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { HomeViewModel() }
}
