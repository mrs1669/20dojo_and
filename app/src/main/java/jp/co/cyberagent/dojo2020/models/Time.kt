package jp.co.cyberagent.dojo2020.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Time (
    val hour:Int,
    val minute: Int
): Parcelable