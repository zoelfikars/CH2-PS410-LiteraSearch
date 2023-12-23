package dicoding.zulfikar.literasearchapp.data.remote.response

import com.google.gson.annotations.SerializedName
import dicoding.zulfikar.literasearchapp.data.model.Book

data class BookResponse(

    @field:SerializedName("data")
    val data: List<Book>,

    @field:SerializedName("message")
    val message: String? = null
)
