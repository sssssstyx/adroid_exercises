package com.example.launchmap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showMap(view: View) {
        val lat = latEditText.text.toString().toDoubleOrNull()
        val lng = lngEditText.text.toString().toDoubleOrNull()
        println(lat)
        println(lng)
        println("geo:".plus(lat).plus(",").plus(lng))
        if (lat != null && lng != null) {
            val gmmIntentUri: Uri = Uri.parse("geo:".plus(lat).plus(",").plus(lng))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }
    }
}
