package com.example.budget_tracker.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budget_tracker.viewmodel.BudgetViewModel

@Composable
fun AddExpenseScreen(navController: NavHostController, viewModel: BudgetViewModel) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }

    val templates by viewModel.templates.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Transaction", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            FilterChip(
                selected = isExpense,
                onClick = { isExpense = true },
                label = { Text("Expense") }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = !isExpense,
                onClick = { isExpense = false },
                label = { Text("Income") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        if (isExpense) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amt = amount.toDoubleOrNull() ?: 0.0
                if (amt > 0 && description.isNotBlank()) {
                    if (isExpense) viewModel.addExpense(amt, description, category)
                    else viewModel.addIncome(amt, description)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        if (isExpense && templates.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Quick Add from Past", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(templates) { t ->
                    Button(
                        onClick = {
                            viewModel.addExpense(t.amount, t.description, t.category)
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        Text("${t.category.ifBlank { "Other" }} - ₹${"%.2f".format(t.amount)}")
                    }
                }
            }
        }
    }
}
