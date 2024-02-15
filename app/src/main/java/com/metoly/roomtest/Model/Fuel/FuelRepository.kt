package com.metoly.roomtest.Model.Fuel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class FuelRepository(private val fuelDao: FuelDao) {

    val allFuel: Flow<List<Fuel>> = fuelDao.getAllFuel()

    suspend fun insert(fuel: Fuel) {
        fuelDao.insert(fuel)
    }

    suspend fun update(fuel: Fuel) {
        fuelDao.update(fuel)
    }

    suspend fun deleteAll() {
        fuelDao.deleteAll()
    }

    suspend fun deleteById(id: Int) {
        fuelDao.deleteById(id)
    }

    fun getFuelById(id: Int): LiveData<Fuel> {
        return fuelDao.getFuelById(id)
    }
}
