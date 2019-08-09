package com.example.angel.views

import android.content.Context
import android.widget.ImageView
import android.widget.TextView


class UserView(context: Context) {
    var profilePhoto = ImageView(context)
    var usernameTextView = TextView(context)
    var id: String = ""
}