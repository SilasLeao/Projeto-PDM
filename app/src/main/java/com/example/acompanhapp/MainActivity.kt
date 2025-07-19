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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        composable("dashboard") { DashboardFamiliarScreen(navController) }
        composable("equipe") { EquipeMedicaScreen(navController) }
    }
}