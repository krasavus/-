package com.example.bankingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankingapp.ui.theme.BankingAppTheme

data class ConfirmationDetailItem(val label: String, val value: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(
    operationTitle: String,
    details: List<ConfirmationDetailItem>,
    onConfirm: () -> Unit,
    onCancel: () -> Unit // Для кнопки "Отмена" или "Назад"
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(operationTitle) },
                navigationIcon = {
                    IconButton(onClick = onCancel) { // Используем onCancel для иконки назад
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Отмена")
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Пожалуйста, проверьте детали операции:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    details.forEachIndexed { index, detail ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp), // Увеличил отступ для лучшей читаемости
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${detail.label}:", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = detail.value,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary // Выделим значение цветом
                            )
                        }
                        if (index < details.size - 1) { // Не добавлять Divider после последнего элемента
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Отменить")
                }
                Button(
                    onClick = {
                        onConfirm()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Подтвердить")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Confirmation Transfer")
@Composable
fun ConfirmationScreenTransferPreview() {
    BankingAppTheme {
        ConfirmationScreen(
            operationTitle = "Подтверждение перевода",
            details = listOf(
                ConfirmationDetailItem("Счет списания", "Зарплатная карта"),
                ConfirmationDetailItem("Получатель", "Иванов П. С."),
                ConfirmationDetailItem("Счет получателя", "**** **** **** 1234"),
                ConfirmationDetailItem("Сумма", "1500.00 RUB"),
                ConfirmationDetailItem("Комиссия", "0.00 RUB"),
                ConfirmationDetailItem("Итого", "1500.00 RUB")
            ),
            onConfirm = {},
            onCancel = {}
        )
    }
}

@Preview(showBackground = true, name = "Confirmation Payment")
@Composable
fun ConfirmationScreenPaymentPreview() {
    BankingAppTheme {
        ConfirmationScreen(
            operationTitle = "Подтверждение платежа",
            details = listOf(
                ConfirmationDetailItem("Услуга", "Моб. связь (Мегафон)"),
                ConfirmationDetailItem("Номер телефона", "+7 (921) 123-45-67"),
                ConfirmationDetailItem("Сумма", "250.50 RUB"),
                ConfirmationDetailItem("Комиссия", "0.00 RUB"),
                ConfirmationDetailItem("Итого к списанию", "250.50 RUB")
            ),
            onConfirm = {},
            onCancel = {}
        )
    }
}
