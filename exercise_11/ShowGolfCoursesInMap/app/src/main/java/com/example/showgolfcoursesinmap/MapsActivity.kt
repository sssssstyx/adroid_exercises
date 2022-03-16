package com.example.showgolfcoursesinmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindow(this))

        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here
        val url = "https://ptm.fi/materials/golfcourses/golf_courses.json"
        // Create request and listeners
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    // Get employees from JSON
                    val courses = response.getJSONArray("courses")
                    // Log.d("JSON:", courses.length().toString())
                    // Log.d("JSON",courses.toString())

                    val colorHues = mapOf(
                        "Kulta" to BitmapDescriptorFactory.HUE_AZURE,
                        "Etu" to BitmapDescriptorFactory.HUE_GREEN,
                        "Kulta/Etu" to BitmapDescriptorFactory.HUE_BLUE,
                        "?" to BitmapDescriptorFactory.HUE_YELLOW
                    )

                    for (i in 0 until courses.length()) {
                        val course = courses.getJSONObject(i)

                        var color = BitmapDescriptorFactory.HUE_MAGENTA
                        if (colorHues.containsKey(course.getString("type"))) {
                            color = colorHues.getValue(course.getString("type"))
                        }

                        mMap.addMarker(
                            MarkerOptions()
                            .position(LatLng(course.getDouble("lat"), course.getDouble("lng")))
                            // .title(course.getString("course"))
                            // .snippet(course.getString("address").plus(""))
                            .icon(BitmapDescriptorFactory.defaultMarker(color))
                        ).tag = course
                    }
                },
                Response.ErrorListener { error ->
                    Log.d("JSON",error.toString())
                }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}
