package com.nabila.ketapang

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FinanceDatabase.getDatabase(application).financeDao()
    val allFinances: LiveData<List<Finance>> = db.getAllFinances()

    fun addFinance(finance: Finance) {
        viewModelScope.launch { db.insert(finance) }
    }

    fun updateFinance(finance: Finance) {
        viewModelScope.launch {
            db.update(finance.id, finance.date, finance.description, finance.amount, finance.isIncome)
        }
    }

    fun deleteFinance(financeId: Int) {
        viewModelScope.launch { db.delete(financeId) }
    }
}
