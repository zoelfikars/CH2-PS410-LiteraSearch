package dicoding.zulfikar.literasearchapp.data.model

data class BookModel (
    val coverUrl: String,
    val publicationYear: Int,
    val author: String,
    val subject: String,
    val isbn: String,
    val publisher: String,
    val description: String,
    val title: String,
    val idPerpus: Int
)