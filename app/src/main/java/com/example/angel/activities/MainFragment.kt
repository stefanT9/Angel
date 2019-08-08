package com.example.angel.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.angel.R
import kotlinx.android.synthetic.main.fragment_qr.*

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    fun newInstance(): MainFragment {
        return MainFragment()
    }
}
