package com.example.angel.services

import android.util.Log
import com.example.angel.models.User
import com.google.firebase.firestore.FirebaseFirestore

class UserServices() {

    private val context = FirebaseFirestore.getInstance()
    private val userDB = context.collection("users")

    ///TODO: Make updateing to database work
    fun addToDb(user: User) {
        userDB.document(user.id).set(user).addOnCompleteListener()
        {
            if (it.isSuccessful) {
                Log.d("[UserServices]", " Successfully added user ${user.id} to database")
            } else {
                Log.d("[UserServices", it.result.toString())
                Log.d("[UserServices]", " Unsuccessfully added user ${user.id} to database")
            }
        }
    }

    fun addToDb(user: HashMap<String, Any>) {
        userDB.document(user["id"].toString()).set(user).addOnCompleteListener()
        {
            if (it.isSuccessful) {
                Log.d("[UserServices]", " Successfully added user ${user["id"]} to database")
            } else {
                Log.d("[UserServices]", "Unsuccessfully added user ${user["id"]} to database")
            }
        }
    }

    private fun user2hash(user: User): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = user.id
        hashMap["angelsId"] = user.angelsId
        hashMap["cnp"] = user.cnp
        hashMap["guardedId"] = user.guardedId
        hashMap["locations"] = user.locations
        hashMap["name"] = user.name
        hashMap["surename"] = user.surename
        return hashMap
    }

    private fun hash2user(hashMap: HashMap<String, Any>): User {
        val user = User(hashMap)
        return user
    }

}