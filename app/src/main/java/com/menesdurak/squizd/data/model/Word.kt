package com.menesdurak.squizd.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "words_table")
data class Word (
    val name: String,
    val meaning: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}