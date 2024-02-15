package com.metoly.challengefuelapp.Fuel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_table")
data class Fuel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val price: Double,
    val liter: Double,
    val totalPrice: Double
)
