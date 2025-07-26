package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CustomPunishmentActivity : AppCompatActivity() {

    private lateinit var customPunishmentEditText: EditText
    private lateinit var saveCustomPunishmentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_punishment)

        customPunishmentEditText = findViewById(R.id.customPunishmentEditText)
        saveCustomPunishmentButton = findViewById(R.id.saveCustomPunishmentButton)

        saveCustomPunishmentButton.setOnClickListener {
            val customPunishment = customPunishmentEditText.text.toString()
            if (customPunishment.isNotEmpty()) {
                saveCustomPunishment(customPunishment)
                Toast.makeText(this, "Наказание сохранено", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Введите наказание", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCustomPunishment(punishment: String) {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        val customPunishments = sharedPreferences.getStringSet("customPunishments", mutableSetOf())?.toMutableSet()
        customPunishments?.add(punishment)
        val editor = sharedPreferences.edit()
        editor.putStringSet("customPunishments", customPunishments)
        editor.putBoolean("creativeAchievementUnlocked", true)
        editor.apply()
    }
}
