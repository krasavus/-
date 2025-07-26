package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PunishmentActivity : AppCompatActivity() {

    private lateinit var punishmentText: TextView
    private lateinit var punishmentCompleteButton: Button

    private val physicalPunishments = listOf(
        "Сделайте 10 приседаний",
        "Сделайте 5 отжиманий"
    )
    private val intellectualPunishments = listOf(
        "Прочитайте 10 страниц книги",
        "Выучите новое слово на иностранном языке"
    )
    private val socialPunishments = listOf(
        "Позвоните маме и скажите, что любите ее",
        "Сделайте комплимент незнакомому человеку"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punishment)

        punishmentText = findViewById(R.id.punishmentText)
        punishmentCompleteButton = findViewById(R.id.punishmentCompleteButton)

        punishmentText.text = getPunishment()

        punishmentCompleteButton.setOnClickListener {
            finish()
        }
    }

    private fun getPunishment(): String {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        val physicalChecked = sharedPreferences.getBoolean("physicalChecked", true)
        val intellectualChecked = sharedPreferences.getBoolean("intellectualChecked", true)
        val socialChecked = sharedPreferences.getBoolean("socialChecked", true)
        val customPunishments = sharedPreferences.getStringSet("customPunishments", emptySet())

        val availablePunishments = mutableListOf<String>()
        if (physicalChecked) {
            availablePunishments.addAll(physicalPunishments)
        }
        if (intellectualChecked) {
            availablePunishments.addAll(intellectualPunishments)
        }
        if (socialChecked) {
            availablePunishments.addAll(socialPunishments)
        }
        if (customPunishments != null) {
            availablePunishments.addAll(customPunishments)
        }

        return if (availablePunishments.isNotEmpty()) {
            availablePunishments.random()
        } else {
            "Вы не выбрали ни одной категории наказаний и не добавили своих!"
        }
    }
}
