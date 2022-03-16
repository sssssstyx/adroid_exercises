package com.example.showgolfcoursesinmap

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.info.view.*
import org.json.JSONObject

class CustomInfoWindow(val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker?): View {
        val mInfoView = (context as Activity).layoutInflater.inflate(R.layout.info, null)
        val mInfoWindow = p0?.tag as JSONObject

        mInfoView.courseTextView.text = mInfoWindow?.getString("course")
        mInfoView.addressTextView.text = mInfoWindow?.getString("address")
        mInfoView.phoneTextView.text = mInfoWindow?.getString("phone")
        mInfoView.emailTextView.text = mInfoWindow?.getString("email")
        mInfoView.webTextView.text = mInfoWindow?.getString("web")

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}