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
    private var daysWithoutSwearing = 0

    private val achievements = listOf(
        Achievement("Первый шаг", "1 день без ругани", 1),
        Achievement("Неделя тишины", "7 дней без ругани", 7),
        Achievement("Месяц спокойствия", "30 дней без ругани", 30)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        daysWithoutSwearingCounter = findViewById(R.id.daysWithoutSwearingCounter)
        iSworeButton = findViewById(R.id.iSworeButton)

        iSworeButton.setOnClickListener {
            daysWithoutSwearing = 0
            saveData()
            updateUI()
            val intent = Intent(this, PunishmentActivity::class.java)
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
            if (!achievement.isUnlocked && daysWithoutSwearing >= achievement.daysRequired) {
                achievement.isUnlocked = true
                Toast.makeText(this, "Новое достижение: ${achievement.name}", Toast.LENGTH_SHORT).show()
            }
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
