package com.example.luckyfanapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    @ColumnInfo(name = "android_id")
    var androidId: String,

    @ColumnInfo(name = "long_value")
    var timeSpentInApp: String
)