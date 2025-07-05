package com.example.bankingapp.data.models // Или ваш пакет для моделей данных

data class Account(
    val id: String,
    val name: String,
    val balance: Double,
    val currency: String,
    val accountNumber: String // Добавим номер счета для отображения
)
