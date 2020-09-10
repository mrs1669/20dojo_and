package jp.co.cyberagent.dojo2020.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDao {
    @Query( value = "SELECT * FROM tag_list")
    fun loadAllTag(): LiveData<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tagEntity: TagEntity)

    @Delete
    fun delete(tagEntity: TagEntity)

    @Update
    fun updateTag(tagEntity: TagEntity)
}