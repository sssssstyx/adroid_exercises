package com.example.showgolfcoursesinmap

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MarkerClusterRenderer : DefaultClusterRenderer<GolfCourseItem> {
    constructor(
        context: Context?,
        map: GoogleMap?,
        clusterManager: ClusterManager<GolfCourseItem>?
    ) : super(context, map, clusterManager)

    private val colorHues = mapOf(
        "Kulta" to BitmapDescriptorFactory.HUE_AZURE,
        "Etu" to BitmapDescriptorFactory.HUE_GREEN,
        "Kulta/Etu" to BitmapDescriptorFactory.HUE_BLUE,
        "?" to BitmapDescriptorFactory.HUE_YELLOW
    )
    override fun onBeforeClusterItemRendered(item: GolfCourseItem?, markerOptions: MarkerOptions?) {
        val course = item?.mCourse
        var color = BitmapDescriptorFactory.HUE_MAGENTA
        if (course != null && colorHues.containsKey(course.getString("type"))) {
            color = colorHues.getValue(course.getString("type"))
        }
        // markerOptions!!.icon(BitmapDescriptorFactory.defaultMarker(color))
        markerOptions?.icon(BitmapDescriptorFactory.defaultMarker(color))

        super.onBeforeClusterItemRendered(item, markerOptions)
    }
}