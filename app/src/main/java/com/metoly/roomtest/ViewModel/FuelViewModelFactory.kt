package com.metoly.roomtest.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metoly.roomtest.Model.Fuel.FuelRepository
import com.metoly.roomtest.Model.Fuel.FuelRoomDatabase

class FuelViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuelViewModel::class.java)) {
            val dataSource = FuelRoomDatabase.getDatabase(application).fuelDao()
            val repository = FuelRepository(dataSource)
            @Suppress("UNCHECKED_CAST")
            return FuelViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
