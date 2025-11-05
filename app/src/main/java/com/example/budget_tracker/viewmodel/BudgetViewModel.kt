package com.example.budget_tracker.viewmodel


import androidx.lifecycle.ViewModel
import com.example.budget_tracker.data.Expense
import com.example.budget_tracker.data.Income
import com.example.budget_tracker.data.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.*

class BudgetViewModel : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome.asStateFlow()

    private val _totalExpenses = MutableStateFlow(0.0)
    val totalExpenses: StateFlow<Double> = _totalExpenses.asStateFlow()

    private val _remainingBalance = MutableStateFlow(0.0)
    val remainingBalance: StateFlow<Double> = _remainingBalance.asStateFlow()

    private val _templates = MutableStateFlow<List<Expense>>(emptyList())
    val templates: StateFlow<List<Expense>> = _templates.asStateFlow()

    fun addIncome(amount: Double, description: String) {
        val income = Income(amount, description)
        _transactions.update { it + income }
        updateTotals()
    }

    fun addExpense(amount: Double, description: String, category: String) {
        val expense = Expense(amount, description, category)
        _transactions.update { it + expense }
        if (_templates.value.none { it.category == category }) {
            _templates.update { it + expense }
        }
        updateTotals()
    }

    private fun updateTotals() {
        val incomeTotal = _transactions.value.filterIsInstance<Income>().sumOf { it.amount }
        val expenseTotal = _transactions.value.filterIsInstance<Expense>().sumOf { it.amount }

        _totalIncome.value = incomeTotal
        _totalExpenses.value = expenseTotal
        _remainingBalance.value = incomeTotal - expenseTotal
    }

    fun getExpensesByCategory(): Map<String, Double> {
        return _transactions.value
            .filterIsInstance<Expense>()
            .groupBy { it.category.ifEmpty { "Other" } }
            .mapValues { (_, expenses) -> expenses.sumOf { it.amount } }
    }
}
