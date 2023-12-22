package dicoding.zulfikar.literasearchapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetSpecificBookFromLibraryIdResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("coverUrl")
	val coverUrl: String,

	@field:SerializedName("publication_year")
	val publicationYear: Int,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("subject")
	val subject: List<String>,

	@field:SerializedName("isbn")
	val isbn: String,

	@field:SerializedName("publisher")
	val publisher: String,

	@field:SerializedName("description")
	val description: List<String>,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("id_perpus")
	val idPerpus: Int
)
