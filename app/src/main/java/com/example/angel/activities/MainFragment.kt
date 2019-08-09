package com.example.angel.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.example.angel.R
import com.example.angel.models.User
import com.example.angel.services.UserServices
import com.example.angel.views.CustomAdapter
import com.example.angel.views.UserView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userServices = UserServices()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    fun newInstance(): MainFragment {
        return MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ///startUpdateListener()

        if (auth.currentUser == null) {
            Log.e("[MainFragment", "no user is logged in")
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            TODO("FINISH Activity from fragment")
        }

        val friendsRef = mutableListOf<DocumentReference>()
        val friendViews = mutableListOf<UserView>()
        val adapter = CustomAdapter(friendViews, context!!)
        guarded_listView_mainFragment.adapter = adapter

        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener()
        {
            val user = User(it)
            for ((idx, id) in user.guardedId.withIndex()) {
                Log.e("[MainFragment]", id)
                friendsRef.add(db.collection("users").document(id))
                friendsRef[idx].get().addOnSuccessListener {
                    Log.e("[MainFragment]", "${it.get("name")} ${it.get("surename")}")
                    val userView = UserView(context!!)
                    friendViews.add(userView)
                    userView.id = id
                    userView.usernameTextView.text = "${it.get("surename")} ${it.get("name")}"
                    guarded_listView_mainFragment.adapter = adapter
                }
            }
        }

    }

    private fun startUpdateListener() {
        return
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

