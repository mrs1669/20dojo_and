package jp.co.cyberagent.dojo2020.data

import androidx.lifecycle.LiveData
import jp.co.cyberagent.dojo2020.models.Memo

interface MemoDataSource {
    fun loadAllMemo(): LiveData<List<Memo>>

    fun inputMemo(memo: Memo)

    fun deleteMemo(memo: Memo)

    fun updateMemo(memo: Memo)
}