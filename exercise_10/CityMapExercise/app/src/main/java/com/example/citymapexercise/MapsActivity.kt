package com.example.citymapexercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


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

        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        class City(val name: String, val position: LatLng) {}

        val cities = listOf(
            City("Helsinki", LatLng(60.16952, 24.93545)),
            City("Vantaa", LatLng(60.29414, 25.04099)),
            City("Espoo", LatLng(60.2052, 24.6522)),
            City("Tampere", LatLng(61.49911, 23.78712)),
            City("Kuopio", LatLng(62.89238, 27.67703))
        )

        // val builder = LatLngBounds.builder()
        for (city in cities) {
            mMap.addMarker(MarkerOptions().position(city.position).title("Marker in ${city.name}"))
            // builder.include(city.position)
        }
        // val bounds = builder.build()
        // val padding = 20
        // val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        // mMap.animateCamera(cu)
        // mMap.moveCamera(cu)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cities.first().position))

        // val helsinki = LatLng(60.16952, 24.93545)
        // mMap.addMarker(MarkerOptions().position(helsinki).title("Marker in Helsinki"))
        // val vantaa = LatLng(60.29414, 25.04099)
        // mMap.addMarker(MarkerOptions().position(vantaa).title("Marker in Vantaa"))
        // val espoo = LatLng(60.2052, 24.6522)
        // mMap.addMarker(MarkerOptions().position(espoo).title("Marker in Espoo"))
        // val tampere = LatLng(61.49911, 23.78712)
        // mMap.addMarker(MarkerOptions().position(tampere).title("Marker in Tampere"))
        // val kuopio = LatLng(62.89238, 27.67703)
        // mMap.addMarker(MarkerOptions().position(kuopio).title("Marker in Kuopio"))

    }
}
