package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PunishmentActivity : AppCompatActivity() {

    private lateinit var punishmentText: TextView
    private lateinit var punishmentCompleteButton: Button

    private val punishments = listOf(
        "Сделайте 10 приседаний",
        "Позвоните маме и скажите, что любите ее",
        "Сделайте 5 отжиманий",
        "Прочитайте 10 страниц книги",
        "Посмотрите смешное видео с котиками"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punishment)

        punishmentText = findViewById(R.id.punishmentText)
        punishmentCompleteButton = findViewById(R.id.punishmentCompleteButton)

        punishmentText.text = punishments.random()

        punishmentCompleteButton.setOnClickListener {
            finish()
        }
    }
}
