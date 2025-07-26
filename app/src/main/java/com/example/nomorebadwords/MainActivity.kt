package com.example.nomorebadwords

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var daysWithoutSwearingCounter: TextView
    private lateinit var iSworeButton: Button
    private lateinit var punishmentSettingsButton: Button
    private lateinit var achievementsButton: Button
    private lateinit var statisticsButton: Button
    private lateinit var panicButton: ImageButton
    private var daysWithoutSwearing = 0

    private val achievements = mutableListOf(
        Achievement("Первый шаг", "1 день без ругани", 1),
        Achievement("Неделя тишины", "7 дней без ругани", 7),
        Achievement("Месяц спокойствия", "30 дней без ругани", 30),
        Achievement("Железная воля", "100 дней без ругани", 100),
        Achievement("Святой", "365 дней без ругани", 365),
        Achievement("Любопытный", "Зайти в настройки наказаний", -1, true),
        Achievement("Креативный", "Добавить свое наказание", -1, true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        daysWithoutSwearingCounter = findViewById(R.id.daysWithoutSwearingCounter)
        iSworeButton = findViewById(R.id.iSworeButton)
        punishmentSettingsButton = findViewById(R.id.punishmentSettingsButton)
        achievementsButton = findViewById(R.id.achievementsButton)
        statisticsButton = findViewById(R.id.statisticsButton)
        panicButton = findViewById(R.id.panicButton)

        iSworeButton.setOnClickListener {
            daysWithoutSwearing = 0
            saveData()
            updateUI()
            recordSwearing()
            val intent = Intent(this, PunishmentActivity::class.java)
            startActivity(intent)
        }

        punishmentSettingsButton.setOnClickListener {
            unlockSecretAchievement("Любопытный")
            val intent = Intent(this, PunishmentCategoriesActivity::class.java)
            startActivity(intent)
        }

        achievementsButton.setOnClickListener {
            val intent = Intent(this, AchievementsActivity::class.java)
            intent.putExtra("achievements", achievements as java.io.Serializable)
            startActivity(intent)
        }

        statisticsButton.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        panicButton.setOnClickListener {
            val intent = Intent(this, PanicActivity::class.java)
            startActivity(intent)
        }

        scheduleDailyNotification()
    }

    override fun onResume() {
        super.onResume()
        loadData()
        updateUI()
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("daysWithoutSwearing", daysWithoutSwearing)
        editor.apply()
    }

    private fun recordSwearing() {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val currentCount = sharedPreferences.getInt("swearing_day_$dayOfWeek", 0)
        editor.putInt("swearing_day_$dayOfWeek", currentCount + 1)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        daysWithoutSwearing = sharedPreferences.getInt("daysWithoutSwearing", 0)
    }

    private fun updateUI() {
        daysWithoutSwearingCounter.text = daysWithoutSwearing.toString()
        checkAchievements()
    }

    private fun checkAchievements() {
        for (achievement in achievements) {
            if (!achievement.isUnlocked && !achievement.isSecret && daysWithoutSwearing >= achievement.daysRequired) {
                achievement.isUnlocked = true
                Toast.makeText(this, "Новое достижение: ${achievement.name}", Toast.LENGTH_SHORT).show()
            }
        }
        val sharedPreferences = getSharedPreferences("com.example.nomorebadwords", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("creativeAchievementUnlocked", false)) {
            unlockSecretAchievement("Креативный")
        }
    }

    private fun unlockSecretAchievement(name: String) {
        val achievement = achievements.find { it.name == name }
        if (achievement != null && !achievement.isUnlocked) {
            achievement.isUnlocked = true
            Toast.makeText(this, "Новое секретное достижение: ${achievement.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleDailyNotification() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}
