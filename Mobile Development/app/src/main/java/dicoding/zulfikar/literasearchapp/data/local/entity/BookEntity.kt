package dicoding.zulfikar.literasearchapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    val coverUrl: String,
    val publicationYear: Int,
    val author: String,
    val subject: String,
    @PrimaryKey
    val isbn: String,
    val publisher: String,
    val description: String,
    val title: String,
    val idPerpus: Int,
    val filter: String? = null
)