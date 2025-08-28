package com.example.acompanhapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.acompanhapp.ui.theme.AcompanhAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.acompanhapp.di.appModule
import com.example.acompanhapp.screens.EquipeMedicaScreen
import com.example.acompanhapp.screens.HomeScreen
import com.example.acompanhapp.screens.LoginScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        enableEdgeToEdge()
        setContent {
            AcompanhAppTheme {
                AppNavigation()
            }
        }
    }
}


// Controller de navegação da aplicação
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Rotas da aplicação, tela de login setada como a inicial
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("dashboard") { DashboardFamiliarScreen() }
        composable("equipe") { EquipeMedicaScreen() }
    }
}