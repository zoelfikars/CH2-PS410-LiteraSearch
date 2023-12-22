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
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.data.remote.response.MessageResponse
import dicoding.zulfikar.literasearchapp.data.remote.response.StoryResponse
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService
import dicoding.zulfikar.literasearchapp.view.paging.BookRemoteMediator

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

    suspend fun getStories(): Result<StoryResponse> {
        try {
            val listResponse = apiService.getStories()

            if (listResponse.error) {
                val errorMessage = listResponse.message
                return Result.Error(Exception(errorMessage))
            }
            return Result.Success(listResponse)
        } catch (e: retrofit2.HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
            val errorMessage = errorBody.message
            return Result.Error(Exception(errorMessage))
        }
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
    suspend fun getBook(){

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