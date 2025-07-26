package com.example.nomorebadwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AchievementsAdapter(private val achievements: List<Achievement>, private val onAchievementClick: (Achievement) -> Unit) : RecyclerView.Adapter<AchievementsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievement = achievements[position]
        holder.text1.text = achievement.name
        holder.text2.text = achievement.description
        holder.itemView.alpha = if (achievement.isUnlocked) 1.0f else 0.5f
        holder.itemView.setOnClickListener {
            if (achievement.isUnlocked) {
                onAchievementClick(achievement)
            }
        }
    }

    override fun getItemCount() = achievements.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1: TextView = itemView.findViewById(android.R.id.text1)
        val text2: TextView = itemView.findViewById(android.R.id.text2)
    }
}
