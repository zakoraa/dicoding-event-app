package com.raflis.final_submission_1_android_fundamental.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "ownerName")
    var ownerName: String? = null,

    @ColumnInfo(name = "beginTime")
    var beginTime: String? = null,

    @ColumnInfo(name = "endTime")
    var endTime: String? = null,

    @ColumnInfo(name = "mediaCover")
    var mediaCover: String? = null,
): Parcelable