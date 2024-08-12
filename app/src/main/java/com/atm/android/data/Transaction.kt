package com.atm.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: String,
    val count_2000: Int,
    val count_500: Int,
    val count_200: Int,
    val count_100: Int
)
