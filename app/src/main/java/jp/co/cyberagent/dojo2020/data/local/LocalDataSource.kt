package jp.co.cyberagent.dojo2020.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import jp.co.cyberagent.dojo2020.data.MemoDataSource
import jp.co.cyberagent.dojo2020.data.TagDataSource
import jp.co.cyberagent.dojo2020.data.local.db.AppDatabase
import jp.co.cyberagent.dojo2020.data.local.db.MemoEntity
import jp.co.cyberagent.dojo2020.data.local.db.TagEntity
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.models.Tag


class LocalDataSource (private val database: AppDatabase): MemoDataSource, TagDataSource {
    override fun loadAllMemo(): LiveData<List<Memo>> {
        Log.i("test:", "in LocalDatasource loadAllMemo()" )
        return database.memoDao().loadAllMemo().map {
            memo ->
                memo.map { Memo(it.id, it.tag, it.title, it.hour, it.minute, it.description)}
        }
    }

    override fun inputMemo (memo: Memo) {
        val forInsertMemo = MemoEntity.createForInsert(0, memo.tag, memo.title, memo.hour, memo.minute, memo.description)
        Log.i("test:LocalDatasource", memo.title )

        database.memoDao().insert(forInsertMemo)
    }

    override fun deleteMemo (memo: Memo) {
        val forInsertMemo = MemoEntity.createForInsert(memo.id ,memo.tag,  memo.title, memo.hour, memo.minute, memo.description)
        Log.i("test:LocalDatasource", memo.title )

        database.memoDao().delete(forInsertMemo)
    }

    override fun updateMemo (memo: Memo) {
        val forInsertMemo = MemoEntity.createForInsert(memo.id, memo.tag,   memo.title, memo.hour, memo.minute, memo.description)
        Log.i("test:LocalDatasource", memo.title )

        database.memoDao().updateMemo(forInsertMemo)
    }

    override fun loadAllTag(): LiveData<List<Tag>> {
        return database.tagDao().loadAllTag().map {
                tag ->
            tag.map { Tag(it.tag)}
        }
    }

    override fun inputTag(tag: Tag) {
        val forInsertTag = TagEntity.createForInsert(tag.tag)
        database.tagDao().insert(forInsertTag)
    }
}