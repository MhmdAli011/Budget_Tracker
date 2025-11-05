package com.example.budget_tracker

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budget_tracker.ui.screens.AddExpenseScreen
import com.example.budget_tracker.ui.screens.HomeScreen
import com.example.budget_tracker.ui.screens.StatsScreen
import com.example.budget_tracker.viewmodel.BudgetViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val budgetViewModel: BudgetViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController, viewModel = budgetViewModel)
        }
        composable("add") {
            AddExpenseScreen(navController = navController, viewModel = budgetViewModel)
        }
        composable("stats") {
            StatsScreen(navController = navController, viewModel = budgetViewModel)
        }
    }
}
