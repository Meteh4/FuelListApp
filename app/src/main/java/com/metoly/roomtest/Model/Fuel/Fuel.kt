package com.metoly.roomtest.Model.Fuel

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_table")
data class Fuel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val price: Double,
    val liter: Double,
    val totalPrice: Double,
    val type: String,
    val photo: ByteArray?
)
