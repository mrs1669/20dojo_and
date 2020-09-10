package jp.co.cyberagent.dojo2020.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Memo (
    val id: Int,
    val tag: String,
    val title: String,
    val hour: Int,
    val minute: Int,
    val description: String
): Parcelable