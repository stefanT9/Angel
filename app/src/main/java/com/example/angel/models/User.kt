@file:Suppress("UNCHECKED_CAST")

package com.example.angel.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint

class User {
    var id: String
    var name: String
    var surename: String
    var cnp: String

    var guardedId: MutableList<String>     ///ID's of those guarded by this
    var angelsId: MutableList<String>     ///ID's of this's angels/guardians

    var locations: MutableList<GeoPoint>   ///last locations of this

    constructor() {
        id = ""
        name = ""
        surename = ""
        cnp = ""
        guardedId = mutableListOf()
        angelsId = mutableListOf()
        locations = mutableListOf()
    }

    constructor(user: DocumentSnapshot?) {
        if (user == null) {
            id = ""
            name = ""
            surename = ""
            cnp = ""
            guardedId = mutableListOf()
            angelsId = mutableListOf()
            locations = mutableListOf()
        } else {
            id = user["id"] as String
            name = user["name"] as String
            surename = user["surename"] as String
            cnp = user["cnp"] as String
            guardedId = user["guardedId"] as MutableList<String>
            angelsId = user["angelsId"] as MutableList<String>
            locations = user["locations"] as MutableList<GeoPoint>
        }
    }

    constructor(user: HashMap<String, Any>) {
        id = user["id"] as String
        name = user["name"] as String
        surename = user["surename"] as String
        cnp = user["cnp"] as String
        guardedId = user["guardedId"] as MutableList<String>
        angelsId = user["angelsId"] as MutableList<String>
        locations = user["locations"] as MutableList<GeoPoint>
    }
}