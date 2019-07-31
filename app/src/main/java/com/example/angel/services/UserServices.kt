package com.example.angel.services

import android.content.Context
import android.util.Log
import com.example.angel.models.User
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import kotlin.system.measureTimeMillis

class UserServices(val context: Context) {

    private val userDB = FirebaseFirestore
        .getInstance()
        .collection("users")

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

    fun updateDb(user: User) {
        userDB.document(user.id).set(user)
    }

    fun updateDb(user: HashMap<String, Any>) {
        userDB.document(user["id"].toString()).set(user)
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
        return User(hashMap)
    }

    private fun getUserById(userId: String): User {
        lateinit var user: User
        val status = userDB.document(userId).get().addOnSuccessListener {
            user = User(it)
        }
        while (!status.isSuccessful)
            continue

        return user
    }


    fun becomeAngel(angelId: String, userId: String) {
        userDB.document(userId).get().addOnSuccessListener {
            val user = User(it)
            user.angelsId.add(angelId)
            addToDb(user)
        }
        userDB.document(angelId).get().addOnSuccessListener {
            val user = User(it)
            user.guardedId.add(userId)
            addToDb(user)
        }
    }

    fun isValidId(id: String): Boolean {
        return true
    }

}