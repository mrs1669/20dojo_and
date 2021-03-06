package jp.co.cyberagent.dojo2020.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//DBのテーブル
@Entity(tableName = "tag_list")
data class TagEntity(
//    @PrimaryKey(autoGenerate = true) val id : Int,
////    @ColumnInfo(name = "tag") val tag: String
    @PrimaryKey
    val tag: String
) {
    companion object {
        fun createForInsert(tag: String): TagEntity {
            return TagEntity(
                tag
            )
        }
    }
}