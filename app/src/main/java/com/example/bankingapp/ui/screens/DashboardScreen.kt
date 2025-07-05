package com.example.bankingapp.ui.screens // Убедитесь, что пакет правильный

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankingapp.ui.theme.BankingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAccounts: () -> Unit,
    onNavigateToTransfer: () -> Unit, // <<< Новый параметр
    onNavigateToPayment: () -> Unit   // <<< Новый параметр
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Главный экран") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Добро пожаловать!",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(onClick = onNavigateToAccounts) {
                Text("Мои Счета")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateToTransfer) { // <<< Подключаем обработчик
                Text("Переводы")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateToPayment) { // <<< Подключаем обработчик
                Text("Платежи")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    BankingAppTheme {
        DashboardScreen(
            onNavigateToAccounts = {},
            onNavigateToTransfer = {}, // <<< Для превью
            onNavigateToPayment = {}    // <<< Для превью
        )
    }
}
