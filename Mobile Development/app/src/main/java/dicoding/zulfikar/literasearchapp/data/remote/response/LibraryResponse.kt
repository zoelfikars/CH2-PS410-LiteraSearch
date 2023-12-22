package dicoding.zulfikar.literasearchapp.data.remote.response

import com.google.gson.annotations.SerializedName
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity

data class LibraryResponse(

    @field:SerializedName("data")
    val data: List<BookEntity>,

    @field:SerializedName("message")
    val message: List<BookEntity>,

    @field:SerializedName("status")
    val status: String
)

data class LibraryItem(

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("perpustakaan")
    val perpustakaan: String,

    @field:SerializedName("id_perpus")
    val idPerpus: Int,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("longitude")
    val longitude: String
)
