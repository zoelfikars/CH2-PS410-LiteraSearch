package dicoding.zulfikar.literasearchapp.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dicoding.zulfikar.literasearchapp.data.remote.response.LibraryResponse
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

val libDummy = "{ \"status\": \"success\", \"data\": [ { \"id_perpus\": 173, \"perpustakaan\": \"Perpustakaan Kota Bandung\", \"alamat\": \"Jl. Seram No.2, Citarum, Kec. Bandung Wetan, Kota Bandung, Jawa Barat 40115\", \"longitude\": \"107.613798\", \"latitude\": \"-6.908203\" }, { \"id_perpus\": 213, \"perpustakaan\": \"Perpustakaan ITB\", \"alamat\": \"Kawasan ITB Kampus Ganesa, Jl. Ganesa No.10, Lb. Siliwangi, Kecamatan Coblong, Kota Bandung, Jawa Barat 40132\", \"longitude\": \"107.610734\", \"latitude\": \"-6.888201\" }, { \"id_perpus\": 214, \"perpustakaan\": \"Perpustakaan A\", \"alamat\": \"Kawasan A Kampus Ganesa, Jl. Ganesa No.10, Lb. Siliwangi, Kecamatan Coblong, Kota Bandung, Jawa Barat 40132\", \"longitude\": \"107.610734\", \"latitude\": \"-6.888201\" }, { \"id_perpus\": 215, \"perpustakaan\": \"Perpustakaan B\", \"alamat\": \"Kawasan B Kampus Ganesa, Jl. Ganesa No.10, Lb. Siliwangi, Kecamatan Coblong, Kota Bandung, Jawa Barat 40132\", \"longitude\": \"107.610734\", \"latitude\": \"-6.888201\" }, { \"id_perpus\": 216, \"perpustakaan\": \"Perpustakaan C\", \"alamat\": \"Kawasan C Kampus Ganesa, Jl. Ganesa No.10, Lb. Siliwangi, Kecamatan Coblong, Kota Bandung, Jawa Barat 40132\", \"longitude\": \"107.610734\", \"latitude\": \"-6.888201\" }, { \"id_perpus\": 216, \"perpustakaan\": \"Perpustakaan D\", \"alamat\": \"Kawasan D Kampus Ganesa, Jl. Ganesa No.10, Lb. Siliwangi, Kecamatan Coblong, Kota Bandung, Jawa Barat 40132\", \"longitude\": \"107.610734\", \"latitude\": \"-6.888201\" }, { \"id_perpus\": 913, \"perpustakaan\": \"Perpustakaan UPI\", \"alamat\": \" Jl. Dr. Setiabudi No.229, Isola, Kec. Sukasari, Kota Bandung, Jawa Barat 40154\", \"longitude\": \"107.591423\", \"latitude\": \"-6.860912\" } ] }"

val gsonLib = Gson()
val libResponse = gsonLib.fromJson(libDummy, LibraryResponse::class.java)


val library: List<Library> = libResponse.data