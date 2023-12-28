package dicoding.zulfikar.literasearchapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.PopularBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.SearchedBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.TrendingBookEntity
import dicoding.zulfikar.literasearchapp.data.model.Library
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService
import dicoding.zulfikar.literasearchapp.view.paging.BookRemoteMediator
import dicoding.zulfikar.literasearchapp.view.paging.LibraryBookRemoteMediator
import dicoding.zulfikar.literasearchapp.view.paging.PopularRemoteMediator
import dicoding.zulfikar.literasearchapp.view.paging.SearchedRemoteMediator
import dicoding.zulfikar.literasearchapp.view.paging.TrendingRemoteMediator

class BookRepository private constructor(
    private var apiService: ApiService,
    private val database: BookDatabase
) {
    fun getLibrary(): LiveData<Result<List<Library>>> = liveData {
        emit(Result.Loading)
        try {
            val listResponse = apiService.getLibrary()
            emit(Result.Success(listResponse.data))
        } catch (e: Exception) {
            emit(Result.Error(Exception(e)))
        }
    }

    fun getPagingLibraryBook(id: String): LiveData<PagingData<LibraryBookEntity>>  {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = LibraryBookRemoteMediator(database, apiService, id),
            pagingSourceFactory = {
                database.libraryBookDao().getAllBook()
            }
        ).liveData
    }

    fun getPopularPaging(): LiveData<PagingData<PopularBookEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = PopularRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.popularDao().getAllBook()
            }
        ).liveData
    }

    fun getTrendingPaging(): LiveData<PagingData<TrendingBookEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = TrendingRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.trendingDao().getAllBook()
            }
        ).liveData
    }

    fun getBookPaging(): LiveData<PagingData<BookEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = BookRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.bookDao().getAllBook()
            }
        ).liveData
    }

    fun searchBook(key: String): LiveData<PagingData<SearchedBookEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = SearchedRemoteMediator(database, apiService, key),
            pagingSourceFactory = {
                database.searchDao().getAllBook()
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(
            apiService: ApiService,
            database: BookDatabase
        ): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(
                    apiService,
                    database
                )
            }.also { instance = it }
    }

}