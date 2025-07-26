package com.example.nomorebadwords

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class AppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val sharedPreferences = context.getSharedPreferences("com.example.nomorebadwords", Context.MODE_PRIVATE)
        val daysWithoutSwearing = sharedPreferences.getInt("daysWithoutSwearing", 0)

        val views = RemoteViews(context.packageName, R.layout.app_widget)
        views.setTextViewText(R.id.widget_days_counter, daysWithoutSwearing.toString())

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
