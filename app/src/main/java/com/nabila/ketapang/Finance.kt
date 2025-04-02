package com.nabila.ketapang

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Finance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val description: String,
    val amount: Int,
    val isIncome: Boolean
)