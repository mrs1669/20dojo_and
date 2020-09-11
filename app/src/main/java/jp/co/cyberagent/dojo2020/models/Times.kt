package jp.co.cyberagent.dojo2020.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Times (
    val times: MutableList<GraphData>
): Parcelable