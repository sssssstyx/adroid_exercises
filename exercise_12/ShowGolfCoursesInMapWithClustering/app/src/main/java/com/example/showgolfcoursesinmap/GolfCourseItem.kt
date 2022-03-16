package com.example.showgolfcoursesinmap

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import org.json.JSONObject


class GolfCourseItem : ClusterItem {
    private val mPosition: LatLng
    private val mTitle: String
    private val mSnippet: String

    val mCourse: JSONObject

    constructor(course: JSONObject) {
        mCourse = course

        mPosition = LatLng(course.getDouble("lat"), course.getDouble("lng"))
        mTitle = course.getString("course")
        mSnippet = course.getString("address")
    }

    override fun getPosition(): LatLng {
        return mPosition
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSnippet(): String {
        return mSnippet
    }
}
