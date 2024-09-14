package com.example.luckyfanapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "android_id")
    var androidId: String = "",

    @ColumnInfo(name = "long_value")
    var timeSpentInApp: String = "",

    @Ignore
    var login: String = "",

    @Ignore
    var password: String = "",

    @Ignore
    private var email: String = ""
) {
    //Room Constructor
    constructor(androidId: String, timeSpentInApp: String) : this(androidId, timeSpentInApp, "", "", "")

    //Firestore Constructor
    @Ignore
    constructor(login: String, email: String, password: String) : this("", "", login, password, email)

    //No param Constructor
    constructor() : this("", "")

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "login" to login,
            "password" to password,
            "email" to email
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): User {
            return User(
                login = map["login"] as? String ?: "",
                password = map["password"] as? String ?: "",
                email = map["email"] as? String ?: ""
            )
        }
    }
}
