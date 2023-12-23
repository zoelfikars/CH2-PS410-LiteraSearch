package dicoding.zulfikar.literasearchapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Library(

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
) : Parcelable
