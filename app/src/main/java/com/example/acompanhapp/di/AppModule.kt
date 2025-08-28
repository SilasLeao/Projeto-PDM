package com.example.acompanhapp.di

import android.content.Context
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.viewmodel.DashboardViewModel
import com.example.acompanhapp.viewmodel.EquipeMedicaViewModel
import com.example.acompanhapp.viewmodel.HomeViewModel
import com.example.acompanhapp.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // UserPreferences
    single { androidContext().let { UserPreferences(it) } }

    // Retrofit API
    single { RetrofitClient.getClient() }

    // ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { EquipeMedicaViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { HomeViewModel() }
}
