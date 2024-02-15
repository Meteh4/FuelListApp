package com.metoly.roomtest.Model.Fuel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Fuel::class], version = 2, exportSchema = false)
abstract class FuelRoomDatabase : RoomDatabase() {

    abstract fun fuelDao(): FuelDao

    companion object {
        @Volatile
        private var INSTANCE: FuelRoomDatabase? = null

        fun getDatabase(context: Context): FuelRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FuelRoomDatabase::class.java,
                    "fuel_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
