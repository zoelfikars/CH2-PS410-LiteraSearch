package dicoding.zulfikar.literasearchapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_book")
data class PopularBookEntity(
    val coverUrl: String,
    val publicationYear: Int,
    val author: String,
    val subject: String ? = null,
    @PrimaryKey
    val isbn: String,
    val publisher: String,
    val description: String ? = null,
    val title: String,
    val idPerpus: Int
//    val idPerpus: List<Int>
)

