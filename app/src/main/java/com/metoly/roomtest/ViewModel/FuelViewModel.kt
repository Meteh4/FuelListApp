package com.metoly.roomtest.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metoly.roomtest.Model.Fuel.Fuel
import com.metoly.roomtest.Model.Fuel.FuelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FuelViewModel(private val repository: FuelRepository) : ViewModel() {

    val allFuel: Flow<List<Fuel>> = repository.allFuel

    fun insert(fuel: Fuel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(fuel)
    }

    fun update(fuel: Fuel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(fuel)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }

    fun getFuelById(id: Int): LiveData<Fuel> {
        return repository.getFuelById(id)
    }
}
