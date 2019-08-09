package com.example.angel.views

import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.google.firebase.firestore.GeoPoint
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toDrawable
import com.example.angel.models.User
import java.util.zip.Inflater


class UserView(context: Context) {
    var profilePhoto = ImageView(context)
    var usernameTextView = TextView(context)
    var id: String = ""
}