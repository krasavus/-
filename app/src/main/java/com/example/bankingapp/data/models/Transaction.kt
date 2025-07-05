package com.example.bankingapp.data.models

import java.util.Date

enum class TransactionType {
    TRANSFER_INTERNAL, // Перевод между своими счетами
    TRANSFER_EXTERNAL, // Перевод на другой счет/карту
    PAYMENT_SERVICE,   // Оплата услуги
    DEPOSIT,           // Пополнение
    WITHDRAWAL         // Снятие
    // Можно добавить другие типы по мере необходимости
}

data class Transaction(
    val id: String,
    val amount: Double,
    val currency: String,
    val description: String?, // Описание/назначение платежа
    val timestamp: Date,
    val transactionType: TransactionType,
    val senderAccountId: String?, // ID счета отправителя (может быть null для пополнений извне)
    val receiverAccountId: String?, // ID счета получателя (внутренний перевод)
    val receiverDetails: String?, // Реквизиты получателя для внешних переводов или оплат (номер карты, счета, телефона и т.д.)
    val serviceProvider: String? // Наименование поставщика услуги (для PAYMEN_SERVICE)
)
{
    // Конструктор для удобства создания внутренних переводов
    constructor(
        id: String,
        amount: Double,
        currency: String,
        description: String?,
        timestamp: Date,
        senderAccountId: String,
        receiverAccountId: String
    ) : this(
        id = id,
        amount = amount,
        currency = currency,
        description = description,
        timestamp = timestamp,
        transactionType = TransactionType.TRANSFER_INTERNAL,
        senderAccountId = senderAccountId,
        receiverAccountId = receiverAccountId,
        receiverDetails = null,
        serviceProvider = null
    )

    // Конструктор для удобства создания оплаты услуг
    constructor(
        id: String,
        amount: Double,
        currency: String,
        description: String?,
        timestamp: Date,
        senderAccountId: String,
        serviceProvider: String,
        paymentDetails: String // Например, номер телефона или лицевого счета
    ) : this(
        id = id,
        amount = amount,
        currency = currency,
        description = description,
        timestamp = timestamp,
        transactionType = TransactionType.PAYMENT_SERVICE,
        senderAccountId = senderAccountId,
        receiverAccountId = null,
        receiverDetails = paymentDetails,
        serviceProvider = serviceProvider
    )
}
