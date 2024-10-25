package com.raflis.final_submission_1_android_fundamental.data.local.entity

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
    var id: Int? = null,

    @ColumnInfo(name = "summary")
    var summary: String? = null,

    @ColumnInfo(name = "mediaCover")
    var mediaCover: String? = null,

    @ColumnInfo(name = "registrants")
    var registrants: Int? = null,

    @ColumnInfo(name = "imageLogo")
    var imageLogo: String? = null,

    @ColumnInfo(name = "link")
    var link: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "ownerName")
    var ownerName: String? = null,

    @ColumnInfo(name = "cityName")
    var cityName: String? = null,

    @ColumnInfo(name = "quota")
    var quota: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "beginTime")
    var beginTime: String? = null,

    @ColumnInfo(name = "endTime")
    var endTime: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

) : Parcelable