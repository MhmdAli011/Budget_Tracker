package com.example.budget_tracker.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budget_tracker.viewmodel.BudgetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavHostController, viewModel: BudgetViewModel) {
    val expensesByCategory = viewModel.getExpensesByCategory()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val remainingBalance by viewModel.remainingBalance.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Expense Statistics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Income: ₹${"%.2f".format(totalIncome)}", style = MaterialTheme.typography.bodyLarge)
                    Text("Expenses: ₹${"%.2f".format(totalExpenses)}", style = MaterialTheme.typography.bodyLarge)
                    Text("Balance: ₹${"%.2f".format(remainingBalance)}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (expensesByCategory.isEmpty()) {
                Text("No expense data available.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                Text("Expenses by Category", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(expensesByCategory.toList()) { (category, amount) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(category.ifBlank { "Other" }, fontWeight = FontWeight.Medium)
                            Text("₹${"%.2f".format(amount)}")
                        }
                    }
                }
            }
        }
    }
}
