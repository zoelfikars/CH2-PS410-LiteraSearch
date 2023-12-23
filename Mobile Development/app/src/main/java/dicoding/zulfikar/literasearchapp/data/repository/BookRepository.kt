package dicoding.zulfikar.literasearchapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.model.Library
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.data.remote.response.LibraryResponse
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService
import dicoding.zulfikar.literasearchapp.view.book.BookRemoteMediator

class BookRepository private constructor(
    private var apiService: ApiService,
    private val bookDatabase: BookDatabase
) {
    fun getBookTrending(): LiveData<PagingData<BookEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = BookRemoteMediator(bookDatabase, apiService, "Trending"),
            pagingSourceFactory = {
                bookDatabase.bookDao().getBookPaging()
            }
        ).liveData
    }

//    suspend fun getBook(): Result<GetSpecificBookFromLibraryIdResponse> {
//        return try {
//            val result = apiService.getAllBook()
//            val mapped = result.data.map { item ->
//                BookEntity(
//                    coverUrl = item.coverUrl,
//                    publicationYear = item.publicationYear,
//                    author = item.author,
//                    subject = item.subject.joinToString(", "),
//                    isbn = item.isbn,
//                    publisher = item.publisher,
//                    description = item.description.joinToString(", "),
//                    title = item.title,
//                    idPerpus = item.idPerpus
//                )
//            }
//            return Result.Success(result)
//        } catch (e: Exception) {
//            Log.e("BookRepository", "Error fetching book data", e)
//            return Result.Error(Exception(e.message))
//        }
//    }
    suspend fun getBook() {

    }

    suspend fun getLibrary(): Result<List<Library>> {
        Result.Loading
        try {
            val listResponse = apiService.getLibrary()
            return Result.Success(listResponse.data)
        } catch (e: retrofit2.HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LibraryResponse::class.java)
            val errorMessage = errorBody.message
            return Result.Error(Exception(errorMessage))
        }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(
            apiService: ApiService,
            bookDatabase: BookDatabase
        ): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(
                    apiService, bookDatabase
                )
            }.also { instance = it }
    }

}