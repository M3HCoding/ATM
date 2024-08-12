package com.atm.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val denomination: Int,
    var count: Int
)

