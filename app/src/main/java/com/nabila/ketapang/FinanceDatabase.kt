package com.nabila.ketapang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Finance::class], version = 1)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun financeDao(): FinanceDao

    companion object {
        @Volatile private var instance: FinanceDatabase? = null
        fun getDatabase(context: Context): FinanceDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java, "finance_db"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}