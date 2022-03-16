package com.example.showgolfcoursesinmap

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.info.view.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mClusterManager: ClusterManager<GolfCourseItem>
    private lateinit var clickedItem: GolfCourseItem

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

        mClusterManager = ClusterManager(this, mMap)
        mClusterManager.renderer = MarkerClusterRenderer(this, mMap, mClusterManager)
        mClusterManager.markerCollection.setInfoWindowAdapter(CustomInfoWindowAdapter(this))
        mClusterManager.setOnClusterItemClickListener { item: GolfCourseItem ->
            clickedItem = item
            false
        }

        // mMap.setInfoWindowAdapter(CustomInfoWindow(this))

        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)

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

                    for (i in 0 until courses.length()) {
                        val course = courses.getJSONObject(i)

                        mClusterManager.addItem(GolfCourseItem(course))
                    }
                },
                Response.ErrorListener { error ->
                    Log.d("JSON",error.toString())
                }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    internal inner class CustomInfoWindowAdapter(private val context: Context) : InfoWindowAdapter {
        override fun getInfoContents(p0: Marker?): View? {
            val mInfoView = (context as Activity).layoutInflater.inflate(R.layout.info, null)
            val mInfoWindow = clickedItem.mCourse

            mInfoView.courseTextView.text = mInfoWindow.getString("course")
            mInfoView.addressTextView.text = mInfoWindow.getString("address")
            mInfoView.phoneTextView.text = mInfoWindow.getString("phone")
            mInfoView.emailTextView.text = mInfoWindow.getString("email")
            mInfoView.webTextView.text = mInfoWindow.getString("web")

            return mInfoView
        }

        override fun getInfoWindow(p0: Marker?): View? {
            return null
        }
    }
}
