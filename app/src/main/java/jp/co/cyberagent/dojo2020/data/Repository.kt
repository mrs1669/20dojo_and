package jp.co.cyberagent.dojo2020.data

import android.util.Log
import androidx.lifecycle.LiveData
import jp.co.cyberagent.dojo2020.data.local.LocalDataSource
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.models.Tag

interface Repository {
    suspend fun inputMemo(memo: Memo)
    fun loadAllMemo(): LiveData<List<Memo>>
    suspend fun deleteMemo(memo: Memo)
    suspend fun updateMemo(memo: Memo)
    suspend fun inputTag(tag: Tag)
    fun loadAllTag(): LiveData<List<Tag>>
}

class DefaultRepository(
    private val localDataSource: LocalDataSource
):Repository {
    override suspend fun inputMemo(memo: Memo) {
        Log.i("test: in Repository", memo.title)
        localDataSource.inputMemo(memo)
    }

    override fun loadAllMemo(): LiveData<List<Memo>> {
        val localMemoList = localDataSource.loadAllMemo()
        return localMemoList
    }

    override suspend fun deleteMemo(memo: Memo) {
        localDataSource.deleteMemo(memo)
    }

    override suspend fun updateMemo(memo: Memo) {
        localDataSource.updateMemo(memo)
    }

    override suspend fun inputTag(tag: Tag) {
        Log.i("test: in Repository", tag.tag)
        localDataSource.inputTag(tag)
    }

    override fun loadAllTag(): LiveData<List<Tag>> {
        val localTagList = localDataSource.loadAllTag()
        return localTagList
    }
}