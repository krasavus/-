package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AchievementsActivity : AppCompatActivity() {

    private lateinit var achievementsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        achievementsRecyclerView = findViewById(R.id.achievementsRecyclerView)
        achievementsRecyclerView.layoutManager = LinearLayoutManager(this)

        val achievements = intent.getSerializableExtra("achievements") as? List<Achievement>
        if (achievements != null) {
            achievementsRecyclerView.adapter = AchievementsAdapter(achievements) { achievement ->
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Я получил достижение '${achievement.name}' в приложении No More Bad Words!")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }
}
