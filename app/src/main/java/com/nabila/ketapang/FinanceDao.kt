package com.nabila.ketapang

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FinanceDao {
    @Insert
    suspend fun insert(finance: Finance)

    @Query("SELECT * FROM Finance ORDER BY id DESC")
    fun getAllFinances(): LiveData<List<Finance>>

    @Query("DELETE FROM Finance WHERE id = :financeId")
    suspend fun delete(financeId: Int)

    @Query("UPDATE Finance SET date = :date, description = :description, amount = :amount, isIncome = :isIncome WHERE id = :id")
    suspend fun update(id: Int, date: String, description: String, amount: Int, isIncome: Boolean)
}
