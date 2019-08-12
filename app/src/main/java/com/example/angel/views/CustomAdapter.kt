package com.example.angel.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.angel.R
import com.example.angel.activities.GpsActivity


class CustomAdapter(private val views: MutableList<UserView>, val context: Context) : BaseAdapter() {

    @SuppressLint("InflateParams")
    override fun getView(idx: Int, convertView: View?, viewGroup: ViewGroup): View {
        var holder = UserView(context)
        var cView = convertView

        if (cView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cView = inflater.inflate(R.layout.guarded_view, null, true)


            holder.usernameTextView = cView.findViewById(R.id.fullName_textView_guardedView)
            holder.profilePhoto = cView.findViewById(R.id.profilePhoto_ImageView_guardedView)
            holder.id = views[idx].id
            cView.tag = holder
        } else {
            holder = cView.tag as UserView
        }

        holder.usernameTextView.text = views[idx].usernameTextView.text
        cView!!.setOnClickListener()
        {
            createPopup(holder)
            Toast.makeText(context, "Works ${holder.usernameTextView.text}", Toast.LENGTH_SHORT).show()
        }
        return cView
    }

    private fun createPopup(holder: UserView) {
        val intent = Intent(context, GpsActivity::class.java)
        intent.putExtra("userId", holder.id)
        startActivity(context, intent, null)
    }

    override fun getItem(idx: Int): Any {
        return views[idx]
    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return views.size
    }

}