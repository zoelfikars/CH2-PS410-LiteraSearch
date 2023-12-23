package dicoding.zulfikar.literasearchapp.data.remote.response

import com.google.gson.annotations.SerializedName
import dicoding.zulfikar.literasearchapp.data.model.Library

data class LibraryResponse(

    @field:SerializedName("data")
    val data: List<Library>,

    @field:SerializedName("status")
    val message: String
)
