package com.example.budget_tracker.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budget_tracker.viewmodel.BudgetViewModel
import com.example.budget_tracker.data.Expense
import com.example.budget_tracker.data.Income
import com.example.budget_tracker.data.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: BudgetViewModel) {
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val remainingBalance by viewModel.remainingBalance.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SummaryCard(
                income = totalIncome,
                expense = totalExpenses,
                balance = remainingBalance
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text("Recent Transactions", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            if (transactions.isEmpty()) {
                Text("No transactions yet.", color = Color.Gray)
            } else {
                LazyColumn {
                    items(transactions.reversed()) { transaction ->
                        TransactionRow(transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(income: Double, expense: Double, balance: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B7B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Balance", color = Color.White)
            Text("₹${"%.2f".format(balance)}", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Income", color = Color.White)
                    Text("₹${"%.2f".format(income)}", color = Color.White)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Expense", color = Color.White)
                    Text("₹${"%.2f".format(expense)}", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun TransactionRow(transaction: Transaction) {
    val (color, sign) = when (transaction) {
        is Income -> Pair(Color(0xFF0A8A6F), "+")
        is Expense -> Pair(Color(0xFFB00020), "-")
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(transaction.description, fontWeight = FontWeight.Medium)
            if (transaction is Expense)
                Text(transaction.category.ifBlank { "Other" }, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }
        Text("$sign ₹${"%.2f".format(transaction.amount)}", color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("stats") },
            label = { Text("Stats") },
            icon = { Icon(Icons.Default.List, contentDescription = "Stats") }
        )
    }
}
