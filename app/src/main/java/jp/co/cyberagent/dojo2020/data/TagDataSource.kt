package jp.co.cyberagent.dojo2020.data

import androidx.lifecycle.LiveData
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.models.Tag

interface TagDataSource {
    fun loadAllTag(): LiveData<List<Tag>>

    fun inputTag(tag: Tag)
}