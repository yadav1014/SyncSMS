package com.shai.syncsms.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shai.syncsms.util.DateUtility.longToPresentableDate

@Entity(tableName = "sms")
data class Sms(
    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "synced") var synced: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0

    fun getFormattedDate(): String {
        return longToPresentableDate(date)
    }
}
