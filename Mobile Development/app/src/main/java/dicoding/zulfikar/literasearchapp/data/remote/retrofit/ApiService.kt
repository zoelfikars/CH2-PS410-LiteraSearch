package dicoding.zulfikar.literasearchapp.data.remote.retrofit

import dicoding.zulfikar.literasearchapp.data.remote.response.BookResponse
import dicoding.zulfikar.literasearchapp.data.remote.response.LibraryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("all/")
    suspend fun getAllBook(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BookResponse

    @GET("search/{searchedText}")
    suspend fun searchBook(
        @Path("searchedText") searchedText: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BookResponse

    @GET("popular")
    suspend fun getPopular(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BookResponse

    @GET("info/{isbn}")
    suspend fun getBookDescription(
        @Path("isbn") isbn: String
    ): BookResponse //info book

    @GET("subject/{category}")
    suspend fun getBookBySubject(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BookResponse

    @GET("trending/{timespan}")
    suspend fun getTrendingBook(
        @Path("timespan") timespan: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BookResponse

    @GET("libraries")
    suspend fun getLibrary(
//        @Query("page") page: Int = 1,
//        @Query("size") size: Int = 20 //incase we had a lot more library data
    ): LibraryResponse

    @GET("library/{id}")
    suspend fun getBookByLibraryId(
        @Path("id") id: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): BookResponse
}