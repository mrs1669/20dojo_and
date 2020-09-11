package jp.co.cyberagent.dojo2020.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import jp.co.cyberagent.dojo2020.models.Time

@Dao
interface MemoDao {
    @Query( value = "SELECT * FROM memo_list")
    fun loadAllMemo(): LiveData<List<MemoEntity>>

    @Query( value = "SELECT hour, minute FROM memo_list WHERE tag = :tagName")
    fun selectGraphData(tagName: String): List<Time>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memoEntity: MemoEntity)

    @Delete
    fun delete(memoEntity: MemoEntity)

    @Update
    fun updateMemo(memoEntity: MemoEntity)
}