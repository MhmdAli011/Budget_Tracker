package com.example.budget_tracker.data


import java.util.*

sealed class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val date: Date = Date(),
    val description: String
)

class Income(
    amount: Double,
    description: String
) : Transaction(amount = amount, description = description)

class Expense(
    amount: Double,
    description: String,
    val category: String
) : Transaction(amount = amount, description = description)
