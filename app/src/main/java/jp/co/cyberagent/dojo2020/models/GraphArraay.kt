package jp.co.cyberagent.dojo2020.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GraphArraay (
    val graphDataArray: MutableList<GraphData>
): Parcelable