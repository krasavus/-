package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

class PunishmentCategoriesActivity : AppCompatActivity() {

    private lateinit var physicalCheckBox: CheckBox
    private lateinit var intellectualCheckBox: CheckBox
    private lateinit var socialCheckBox: CheckBox
    private lateinit var saveCategoriesButton: Button
    private lateinit var addCustomPunishmentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punishment_categories)

        physicalCheckBox = findViewById(R.id.physicalCheckBox)
        intellectualCheckBox = findViewById(R.id.intellectualCheckBox)
        socialCheckBox = findViewById(R.id.socialCheckBox)
        saveCategoriesButton = findViewById(R.id.saveCategoriesButton)
        addCustomPunishmentButton = findViewById(R.id.addCustomPunishmentButton)

        loadCategories()

        saveCategoriesButton.setOnClickListener {
            saveCategories()
            Toast.makeText(this, "Категории сохранены", Toast.LENGTH_SHORT).show()
            finish()
        }

        addCustomPunishmentButton.setOnClickListener {
            val intent = Intent(this, CustomPunishmentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveCategories() {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("physicalChecked", physicalCheckBox.isChecked)
        editor.putBoolean("intellectualChecked", intellectualCheckBox.isChecked)
        editor.putBoolean("socialChecked", socialCheckBox.isChecked)
        editor.apply()
    }

    private fun loadCategories() {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        physicalCheckBox.isChecked = sharedPreferences.getBoolean("physicalChecked", true)
        intellectualCheckBox.isChecked = sharedPreferences.getBoolean("intellectualChecked", true)
        socialCheckBox.isChecked = sharedPreferences.getBoolean("socialChecked", true)
    }
}
