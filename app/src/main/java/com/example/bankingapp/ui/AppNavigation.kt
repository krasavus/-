package com.example.bankingapp.ui // Или com.example.bankingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bankingapp.NavRoutes
import com.example.bankingapp.ui.screens.* // Импортируем все экраны

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN_SCREEN
    ) {
        composable(NavRoutes.LOGIN_SCREEN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavRoutes.DASHBOARD_SCREEN) {
                        popUpTo(NavRoutes.LOGIN_SCREEN) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoutes.DASHBOARD_SCREEN) {
            DashboardScreen(
                onNavigateToAccounts = { navController.navigate(NavRoutes.ACCOUNTS_SCREEN) },
                onNavigateToTransfer = { navController.navigate(NavRoutes.TRANSFER_SCREEN) },
                onNavigateToPayment = { navController.navigate(NavRoutes.PAYMENT_SCREEN) }
            )
        }
        composable(NavRoutes.ACCOUNTS_SCREEN) {
            AccountsScreen(
                accounts = dummyAccounts,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.TRANSFER_SCREEN) {
            TransferScreen(
                onNavigateBack = { navController.popBackStack() },
                onProceedToConfirmation = { title, detailsList ->
                    // Преобразуем список ConfirmationDetailItem в строку для передачи
                    // Формат: "label1:value1;label2:value2;..."
                    val detailsString = detailsList.joinToString(";") { "${it.label}:${it.value}" }
                    navController.navigate("${NavRoutes.CONFIRMATION_SCREEN}/$title/$detailsString")
                }
            )
        }
        composable(NavRoutes.PAYMENT_SCREEN) {
            PaymentScreen(
                onNavigateBack = { navController.popBackStack() },
                onProceedToConfirmation = { title, detailsList ->
                    val detailsString = detailsList.joinToString(";") { "${it.label}:${it.value}" }
                    navController.navigate("${NavRoutes.CONFIRMATION_SCREEN}/$title/$detailsString")
                }
            )
        }

        composable(
            route = "${NavRoutes.CONFIRMATION_SCREEN}/{operationTitle}/{detailsString}",
            arguments = listOf(
                navArgument("operationTitle") { type = NavType.StringType },
                navArgument("detailsString") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("operationTitle") ?: "Подтверждение"
            val detailsStr = backStackEntry.arguments?.getString("detailsString") ?: ""
            // Преобразуем строку обратно в список ConfirmationDetailItem
            val detailsList = detailsStr.split(';').mapNotNull {
                val parts = it.split(':', limit = 2)
                if (parts.size == 2) ConfirmationDetailItem(parts[0], parts[1]) else null
            }

            ConfirmationScreen(
                operationTitle = title,
                details = detailsList,
                onConfirm = {
                    // Действие после подтверждения: например, показать сообщение об успехе и вернуться на главный экран
                    println("Операция '$title' подтверждена. Детали: $detailsList")
                    // Можно добавить всплывающее сообщение (Toast/Snackbar)
                    navController.navigate(NavRoutes.DASHBOARD_SCREEN) {
                        popUpTo(NavRoutes.DASHBOARD_SCREEN) { inclusive = true } // Возврат на главный экран, очищая стек до него
                    }
                },
                onCancel = {
                    navController.popBackStack() // Вернуться на предыдущий экран (Transfer или Payment)
                }
            )
        }
    }
}
