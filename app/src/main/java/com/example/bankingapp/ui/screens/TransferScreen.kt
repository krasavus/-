package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankingapp.ui.theme.BankingAppTheme
// import com.example.bankingapp.ui.screens.ConfirmationDetailItem // Добавить, если еще нет

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    onNavigateBack: () -> Unit,
    onProceedToConfirmation: (String, List<ConfirmationDetailItem>) -> Unit // <<< Новый параметр
) {
    var selectedSenderAccount by remember { mutableStateOf<String?>("Счет списания (заглушка)") } // Пока просто строка
    var receiverAccountOrCard by remember { mutableStateOf("") }
    var amountString by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf<String?>(null) }
    var receiverError by remember { mutableStateOf<String?>(null) }

    // TODO: Реализовать логику получения и выбора счета списания
    // val accountsDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Перевод средств") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Создание перевода",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // TODO: Заменить на DropdownMenu или аналогичный компонент для выбора счета
            OutlinedTextField(
                value = selectedSenderAccount ?: "Выберите счет",
                onValueChange = { /* TODO */ },
                label = { Text("Счет списания") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true // Пока что
            )

            OutlinedTextField(
                value = receiverAccountOrCard,
                onValueChange = {
                    receiverAccountOrCard = it
                    receiverError = null
                                },
                label = { Text("Номер счета/карты получателя") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = receiverError != null,
                singleLine = true
            )
            if (receiverError != null) {
                Text(receiverError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }


            OutlinedTextField(
                value = amountString,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isDigit() || char == '.' || char == ',' }
                    val dotCount = filteredInput.count { char -> char == '.' }
                    val commaCount = filteredInput.count { char -> char == ',' }

                    if (dotCount <= 1 && commaCount == 0) { // Разрешаем одну точку ИЛИ ноль запятых
                        amountString = filteredInput
                    } else if (commaCount <= 1 && dotCount == 0) { // Разрешаем одну запятую ИЛИ ноль точек
                        amountString = filteredInput.replace(',', '.') // Заменяем запятую на точку для обработки
                    }
                    amountError = null
                },
                label = { Text("Сумма") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = amountError != null,
                singleLine = true,
                trailingIcon = { Text(text = "RUB", style = MaterialTheme.typography.bodyMedium) } // Валюта (пока заглушка)
            )
            if (amountError != null) {
                Text(amountError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Комментарий (необязательно)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(modifier = Modifier.weight(1f)) // Занимает оставшееся место, прижимая кнопку вниз

            Button(
                onClick = {
                    // Валидация
                    val amount = amountString.toDoubleOrNull()
                    var isValid = true
                    if (receiverAccountOrCard.isBlank() || receiverAccountOrCard.length < 16) { // Простая проверка длины
                        receiverError = "Номер счета/карты некорректен."
                        isValid = false
                    }
                    if (amount == null || amount <= 0) {
                        amountError = "Сумма должна быть положительной."
                        isValid = false
                    }

                    if (isValid) {
                        val details = listOf(
                            ConfirmationDetailItem("Счет списания", selectedSenderAccount ?: "Не указан"),
                            ConfirmationDetailItem("Счет/карта получателя", receiverAccountOrCard),
                            ConfirmationDetailItem("Сумма", "${String.format("%.2f", amount)} RUB"), // Форматируем сумму
                            ConfirmationDetailItem("Описание", description.ifBlank { "Нет" })
                        )
                        onProceedToConfirmation("Подтверждение перевода", details)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Перевести")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransferScreenPreview() {
    BankingAppTheme {
        TransferScreen(
            onNavigateBack = {},
            onProceedToConfirmation = { _, _ -> } // Пустая лямбда для превью
        )
    }
}
```
