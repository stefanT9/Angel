package com.example.angel.views

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.microsoft.maps.MapRenderMode
import com.microsoft.maps.MapView

class UserView(context: Context) {
    var profilePhoto = ImageView(context)
    var usernameTextView = TextView(context)
    var id: String = ""

}
