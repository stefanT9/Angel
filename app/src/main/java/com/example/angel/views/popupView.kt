package com.example.angel.views

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.microsoft.maps.MapRenderMode
import com.microsoft.maps.MapView
import org.w3c.dom.Text

class popupViewHolder(context: Context) {
    val textView = TextView(context)
    val button = Button(context)
    val button2 = Button(context)
    val mapLayout = FrameLayout(context)
    val mapView = MapView(context, MapRenderMode.RASTER)

}