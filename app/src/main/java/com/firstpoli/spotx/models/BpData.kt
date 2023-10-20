package com.firstpoli.spotx.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firstpoli.spotx.databases.AppDatabase
import kotlinx.android.parcel.Parcelize

@Entity(tableName = AppDatabase.T_BpData)
@Parcelize
data class BpData(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var state: String = "",
    var systolic: Int = 0,
    var diastolic: Int = 0,
    var pulse: Int = 0,
    var datetime: Long = 0,
) : Parcelable {
    fun level(): Int {
        if (systolic in 90..119 && diastolic in 60..79) {
            return 1
        }
        if (systolic in 120..129 && diastolic in 60..79) {
            return 2
        }
        if (systolic < 90 || diastolic < 60) {
            return 0
        }
        if (systolic > 180 || diastolic > 120) {
            return 5
        }
        if (systolic in 140..180 || diastolic in 90..120) {
            return 4
        }
        if (systolic in 130..139 || diastolic in 80..89) {
            return 3
        }
        return 3
    }

}