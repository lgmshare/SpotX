package com.firstpoli.spotx.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(val id: Int, val title: String, val content: String) : Parcelable {

}