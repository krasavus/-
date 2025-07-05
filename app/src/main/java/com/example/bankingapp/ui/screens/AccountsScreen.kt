package com.example.bankingapp.ui.screens // Убедитесь, что пакет правильный

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankingapp.data.models.Account // <<< Импортируйте вашу модель Account
import com.example.bankingapp.ui.theme.BankingAppTheme
import java.text.NumberFormat
import java.util.Locale

// Фиктивные данные для предварительного просмотра и начальной разработки
val dummyAccounts = listOf(
    Account("1", "Зарплатная карта", 125500.75, "RUB", "40817810123456789012"),
    Account("2", "Сберегательный счет", 750000.00, "RUB", "40817810987654321098"),
    Account("3", "Валютный счет USD", 5250.50, "USD", "40817840111223344556"),
    Account("4", "Кредитная карта", -15000.00, "RUB", "40817810555667788990")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    accounts: List<Account>,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои счета") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(accounts) { account ->
                AccountItem(account = account)
            }
        }
    }
}

@Composable
fun AccountItem(account: Account) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = account.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Номер: ${account.accountNumber}", // Отображаем номер счета
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formatCurrency(account.balance, account.currency),
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (account.balance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = account.currency,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun formatCurrency(amount: Double, currencyCode: String): String {
    val locale = when(currencyCode.uppercase()) {
        "RUB" -> Locale("ru", "RU")
        "USD" -> Locale("en", "US")
        "EUR" -> Locale("fr", "FR") // Пример для Евро, можно выбрать другую страну еврозоны
        else -> Locale.getDefault() // Общий случай
    }
    val format = NumberFormat.getCurrencyInstance(locale)
    try {
        val currency = java.util.Currency.getInstance(currencyCode.uppercase())
        format.currency = currency
    } catch (e: Exception) {
        // Если код валюты не распознан, просто возвращаем число с кодом валюты
        return "$amount $currencyCode"
    }
    return format.format(amount)
}


@Preview(showBackground = true)
@Composable
fun AccountsScreenPreview() {
    BankingAppTheme {
        AccountsScreen(accounts = dummyAccounts, onNavigateBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AccountItemPreview() {
    BankingAppTheme {
        AccountItem(account = dummyAccounts[0])
    }
}
