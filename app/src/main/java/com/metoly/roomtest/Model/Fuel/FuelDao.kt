package com.metoly.roomtest.Model.Fuel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.metoly.roomtest.Model.Fuel.Fuel
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelDao {

    @Query("SELECT * FROM fuel_table")
    fun getAllFuel(): Flow<List<Fuel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fuel: Fuel)

    @Update
    suspend fun update(fuel: Fuel)

    @Query("DELETE FROM fuel_table")
    suspend fun deleteAll()

    @Query("DELETE FROM fuel_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM fuel_table WHERE id = :id")
    fun getFuelById(id: Int): LiveData<Fuel>
}
