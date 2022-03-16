package com.example.weatherapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherAppWidgetProvider : AppWidgetProvider() {

    // example call is : https://api.openweathermap.org/data/2.5/weather?q=Jyväskylä&APPID=YOUR_API_KEY&units=metric&lang=fi
    val API_LINK: String = "https://api.openweathermap.org/data/2.5/weather?q="
    val API_ICON: String = "https://openweathermap.org/img/w/"
    val API_KEY: String = "ae9f1be88265286a161c1f49d27751b6"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        // Perform this loop procedure for each App Widget that belongs to this provider
        appWidgetIds.forEach { appWidgetId ->
            // continue coding here...
            // Create an Intent to launch MainActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            // Get the layout for the App Widget and attach an on-click listener
            val views = RemoteViews(context.packageName, R.layout.weather_appwidget)
            views.setOnClickPendingIntent(R.id.cityTextView, pendingIntent)

            // create intent
            val refreshIntent = Intent(context, WeatherAppWidgetProvider::class.java)
            refreshIntent.action = "fi.jamk.weatherapp.REFRESH"
            refreshIntent.putExtra("appWidgetId", appWidgetId)
            // create pending intent
            val refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            // set pending intent to refresh image view
            views.setOnClickPendingIntent(R.id.refreshImageView, refreshPendingIntent)

            // Load weather forecast
            loadWeatherForecast("Jyväskylä", context, views, appWidgetId, appWidgetManager)

        }
    }

    private fun loadWeatherForecast(
        city:String,
        context: Context,
        views: RemoteViews,
        appWidgetId: Int,
        appWidgetManager: AppWidgetManager)
    {

        // URL to load forecast
        val url = "$API_LINK$city&APPID=$API_KEY&units=metric"

        // continue coding here...

        // JSON object request with Volley
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->
                try {
                    // load OK - parse data from the loaded JSON
                    // **add parse codes here... described later**
                    val mainJSONObject = response.getJSONObject("main")
                    val weatherArray = response.getJSONArray("weather")
                    val firstWeatherObject = weatherArray.getJSONObject(0)

                    // city, condition, temperature
                    val city = response.getString("name")
                    val condition = firstWeatherObject.getString("main")
                    val temperature = mainJSONObject.getString("temp")+" °C"

                    // time
                    val weatherTime: String = response.getString("dt")
                    val weatherLong: Long = weatherTime.toLong()
                    // val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")
                    val weatherDate = Date(weatherLong*1000L)
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    // val dt = Instant.ofEpochSecond(weatherLong).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter).toString()
                    formatter.timeZone = TimeZone.getDefault()
                    // println(TimeZone.getDefault())
                    val dt = formatter.format(weatherDate)
                    // println(dt)

                    views.setTextViewText(R.id.cityTextView, city)
                    views.setTextViewText(R.id.condTextView, condition)
                    views.setTextViewText(R.id.tempTextView, temperature)
                    views.setTextViewText(R.id.timeTextView, dt)

                    // AppWidgetTarget will be used with Glide - image target view
                    val awt: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.iconImageView, views, appWidgetId) {}
                    val weatherIcon = firstWeatherObject.getString("icon")
                    val url = "$API_ICON$weatherIcon.png"

                    Glide
                        .with(context)
                        .asBitmap()
                        .load(url)
                        .into(awt)

                    // Tell the AppWidgetManager to perform an update on the current app widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("WEATRHER", "***** error: $e")
                }
            },
            Response.ErrorListener { error -> Log.d("PTM", "Error: $error") })
        // start loading data with Volley
        val queue = Volley.newRequestQueue(context)
        queue.add(jsonObjectRequest)

    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // got a new action, check if it is refresh action
        if (intent.action == "com.example.weatherapp.REFRESH") {
            // get manager
            val appWidgetManager = AppWidgetManager.getInstance(context.applicationContext)
            // get views
            val views = RemoteViews(context.packageName, R.layout.weather_appwidget)
            // get appWidgetId
            val appWidgetId = intent.extras!!.getInt("appWidgetId")
            // load data again
            loadWeatherForecast("Jyväskylä", context, views, appWidgetId, appWidgetManager)
        }

    }

}
