package com.example.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // Use LinearManager as a layout manager for recyclerView
         recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)

        // URL to JSON data - remember use your own data here
        // public bin
        val url = "https://api.jsonbin.io/b/5e99d03a5fa47104cea26c37"
        // private bin
        // val url = "https://api.jsonbin.io/b/5e99c2b65fa47104cea264e3"
        // val secret_key = "\$2b\$10\$1kE0U1Smp830YyleVeixvO2uItnpNvZUkhSvx./BDL8iqPCoEL646"

        // Create request and listeners
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Get employees from JSON
                val employees = response.getJSONArray("employees")
                Log.d("JSON",employees.toString())

                // Create Employees Adapter with employees data
                recyclerView.adapter = EmployeesAdapter(employees)
            },
            Response.ErrorListener { error ->
                Log.d("JSON",error.toString())
            }
        ) {
            // Providing Request Headers
            // private bin need set header
            /*override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["secret-key"] = secret_key
                return headers
            }*/
        }
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

}
