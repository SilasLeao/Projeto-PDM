package com.example.acompanhapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EquipeMedicaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipeMedicaViewModel::class.java)) {
            return EquipeMedicaViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
