package dicoding.zulfikar.literasearchapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_book")
data class SearchedBookEntity(
    val coverUrl: String,
    val publicationYear: Int,
    val author: String,
    val subject: String,
    @PrimaryKey
    val isbn: String,
    val publisher: String,
    val description: String,
    val title: String,
    val idPerpus: Int
//    val idPerpus: List<Int>
)
