package com.example.bankingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankingapp.ui.theme.BankingAppTheme

// Модель для категории услуг (можно вынести в data/models)
data class ServiceCategory(
    val id: String,
    val name: String,
    val icon: @Composable () -> Unit // Для отображения иконки категории
)

// Фиктивные данные для категорий услуг
val dummyServiceCategories = listOf(
    ServiceCategory("mobile", "Моб. связь") { Icon(Icons.Filled.PhoneAndroid, contentDescription = "Моб. связь") },
    ServiceCategory("utilities", "ЖКХ") { Icon(Icons.Filled.Lightbulb, contentDescription = "ЖКХ") },
    ServiceCategory("internet", "Интернет") { Icon(Icons.Filled.Wifi, contentDescription = "Интернет") },
    ServiceCategory("fines", "Штрафы") { Icon(Icons.Filled.ReceiptLong, contentDescription = "Штрафы") },
    ServiceCategory("taxes", "Налоги") { Icon(Icons.Filled.AccountBalance, contentDescription = "Налоги") }
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onNavigateBack: () -> Unit,
    onProceedToConfirmation: (String, List<ConfirmationDetailItem>) -> Unit // <<< Новый параметр
) {
    var selectedCategory by remember { mutableStateOf<ServiceCategory?>(null) }
    var paymentDetails by remember { mutableStateOf("") } // Номер телефона, лицевой счет и т.д.
    var amountString by remember { mutableStateOf("") }

    var detailsError by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }
    var categoryError by remember { mutableStateOf<String?>(null) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Оплата услуг") },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Выберите услугу для оплаты",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Выбор категории услуги
            Text("Категория услуги:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dummyServiceCategories) { category ->
                    ServiceCategoryChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            categoryError = null
                            // Сбрасываем реквизиты и сумму при смене категории для лучшего UX
                            paymentDetails = ""
                            amountString = ""
                            detailsError = null
                            amountError = null
                        }
                    )
                }
            }
            if (categoryError != null) {
                Text(categoryError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth().padding(start = 8.dp))
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Поля для ввода реквизитов и суммы
            if (selectedCategory != null) {
                OutlinedTextField(
                    value = paymentDetails,
                    onValueChange = {
                        paymentDetails = it
                        detailsError = null
                                    },
                    label = { Text( "Реквизиты для '${selectedCategory!!.name}'") },
                    placeholder = {Text( placeholderTextForCategory(selectedCategory))},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardTypeForCategory(selectedCategory)),
                    isError = detailsError != null,
                    singleLine = true
                )
                if (detailsError != null) {
                    Text(detailsError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = amountString,
                    onValueChange = {
                        val filteredInput = it.filter { char -> char.isDigit() || char == '.' || char == ',' }
                        val dotCount = filteredInput.count { char -> char == '.' }
                        val commaCount = filteredInput.count { char -> char == ',' }

                        if (dotCount <= 1 && commaCount == 0) {
                            amountString = filteredInput
                        } else if (commaCount <= 1 && dotCount == 0) {
                            amountString = filteredInput.replace(',', '.')
                        }
                        amountError = null
                    },
                    label = { Text("Сумма оплаты") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = amountError != null,
                    singleLine = true,
                    trailingIcon = { Text(text = "RUB", style = MaterialTheme.typography.bodyMedium) }
                )
                if (amountError != null) {
                    Text(amountError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            } else {
                 Text("Пожалуйста, выберите категорию услуги.", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(vertical=16.dp))
            }


            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Валидация
                    val amount = amountString.toDoubleOrNull()
                    var isValid = true

                    if (selectedCategory == null) {
                        categoryError = "Пожалуйста, выберите категорию услуги."
                        isValid = false
                    }
                    if (paymentDetails.isBlank() && selectedCategory != null) {
                        detailsError = "Реквизиты не могут быть пустыми."
                        isValid = false
                    }
                    // Дополнительная валидация для номера телефона, если выбрана "Моб. связь"
                    if (selectedCategory?.id == "mobile" && !isValidPhoneNumber(paymentDetails) && selectedCategory != null) {
                        detailsError = "Введите корректный номер телефона (10 цифр)."
                        isValid = false
                    }

                    if ((amount == null || amount <= 0) && selectedCategory != null) {
                        amountError = "Сумма должна быть положительной."
                        isValid = false
                    }


                    if (isValid && selectedCategory != null) {
                        val details = listOf(
                            ConfirmationDetailItem("Услуга", selectedCategory?.name ?: "Не выбрана"),
                            ConfirmationDetailItem(placeholderTextForCategory(selectedCategory).split(" (")[0], paymentDetails), // Используем начало плейсхолдера как метку
                            ConfirmationDetailItem("Сумма", "${String.format("%.2f", amount)} RUB")
                        )
                        onProceedToConfirmation("Подтверждение платежа", details)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedCategory != null // Кнопка активна только если выбрана категория
            ) {
                Text("Оплатить")
            }
        }
    }
}

// Вспомогательные функции для плейсхолдера и типа клавиатуры
fun placeholderTextForCategory(category: ServiceCategory?): String {
    return when (category?.id) {
        "mobile" -> "Номер телефона (10 цифр)"
        "utilities" -> "Номер лицевого счета"
        "internet" -> "Номер договора или логин"
        "fines" -> "Номер УИН"
        "taxes" -> "ИНН или индекс документа"
        else -> "Реквизиты платежа"
    }
}

fun keyboardTypeForCategory(category: ServiceCategory?): KeyboardType {
    return when (category?.id) {
        "mobile" -> KeyboardType.Phone
        "utilities", "fines", "taxes" -> KeyboardType.Number
        else -> KeyboardType.Text
    }
}

fun isValidPhoneNumber(phone: String): Boolean {
    return phone.isNotBlank() && phone.all { it.isDigit() } && phone.length == 10
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceCategoryChip(
    category: ServiceCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(category.name) },
        leadingIcon = category.icon,
        modifier = Modifier.height(AssistChipDefaults.Height)
    )
}


@Preview(showBackground = true, name = "Payment Screen Initial")
@Composable
fun PaymentScreenPreview() {
    BankingAppTheme {
        PaymentScreen(onNavigateBack = {})
    }
}

// Для превью с выбранной категорией, лучше создать обертку, которая управляет состоянием
@Preview(showBackground = true, name = "Payment Screen Mobile Selected")
@Composable
fun PaymentScreenMobileSelectedPreview() {
    val tempSelectedCategory = remember { mutableStateOf<ServiceCategory?>(dummyServiceCategories.firstOrNull { it.id == "mobile" }) }
    // Это превью не будет полностью интерактивным, т.к. состояния управляются внутри PaymentScreen.
    // Чтобы сделать его интерактивным, нужно было бы вынести selectedCategory из PaymentScreen
    // или создать специальный PreviewHost Composable.

    BankingAppTheme {
        // Не самый лучший способ для превью, но для демонстрации вида:
        // Мы не можем напрямую изменить selectedCategory внутри PaymentScreen из этого превью.
        // PaymentScreen сам управляет своим состоянием.
        // Это превью покажет экран, но выбор категории не будет работать в превью.
        // Для интерактивного превью с выбором, нужно было бы передавать состояние selectedCategory
        // в PaymentScreen как параметр и управлять им извне в этом превью.

        // Лучше показать PaymentScreen как есть, а пользователь выберет категорию в интерактивном превью.
        PaymentScreen(onNavigateBack = {})
    }
}

@Preview(showBackground = true, name = "Service Chip Preview")
@Composable
fun ServiceCategoryChipPreview() {
    BankingAppTheme {
        Row {
            ServiceCategoryChip(category = dummyServiceCategories[0], isSelected = true, onClick = {})
            Spacer(Modifier.width(8.dp))
            ServiceCategoryChip(category = dummyServiceCategories[1], isSelected = false, onClick = {})
        }
    }
}
```

Я внес несколько улучшений:
*   Исправил опечатку в имени состояния `amountError`.
*   Добавил иконки из `androidx.compose.material.icons.filled` для категорий услуг.
*   При смене категории теперь сбрасываются поля реквизитов и суммы, а также их ошибки.
*   Добавил вспомогательные функции `placeholderTextForCategory` и `keyboardTypeForCategory` для более релевантных подсказок и типов клавиатуры в поле реквизитов.
*   Добавил простую валидацию для номера телефона (10 цифр), если выбрана категория "Моб. связь".
*   Улучшил превью, добавив `ServiceCategoryChipPreview` и немного изменив `PaymentScreenWithCategorySelectedPreview` (хотя интерактивность выбора категории в превью все еще ограничена тем, как управляются состояния).

Теперь файл `PaymentScreen.kt` должен быть готов.
