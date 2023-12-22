package dicoding.zulfikar.literasearchapp.view.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.RemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val database: BookDatabase,
    private var apiService: ApiService,
    private var string: String,
) : RemoteMediator<Int, BookEntity>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }
            var response = apiService.getAllBook(page, state.config.pageSize)
            if (string.equals("Trending")) {
                response = apiService.getTrendingBook("daily", page, state.config.pageSize)
            }
            if (string.equals("Populer")) {
                response = apiService.getPopular(page, state.config.pageSize)
            }
            if (string.equals("All Book")) {
                response = apiService.getAllBook(page, state.config.pageSize)
            }


            val books = response.data
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.bookDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (books.isEmpty()) null else page + 1

                val keys = books.map {
                    RemoteKeysEntity(id = it.isbn, prevKey = prevKey, nextKey = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)

                val mappedData = books.map { item ->
                    BookEntity(
                        coverUrl = item.coverUrl,
                        publicationYear = item.publicationYear,
                        author = item.author,
                        subject = item.subject.joinToString(", "),
                        isbn = item.isbn,
                        publisher = item.publisher,
                        description = item.description.joinToString(", "),
                        title = item.title,
                        idPerpus = item.idPerpus,
                        filter = string
                    )
                }
                database.bookDao().insertBook(mappedData)
            }

            return MediatorResult.Success(endOfPaginationReached = books.isEmpty())
        } catch (exception: Exception) {
            Log.d("IJUL", "masuk catch : ${exception.printStackTrace()}")
            return MediatorResult.Error(exception)
        }

//        try {
//            val response = apiService.getPopular(page, state.config.pageSize)
//            Log.d("bookremotemediatorijul", "${response.data.get(1).coverUrl}")
//
////            val books = response.
//
//            val endOfPaginationReached = response.data.isEmpty()
//            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    database.remoteKeysDao().deleteRemoteKeys()
//                    database.bookDao().deleteAll()
//                }
//                val prevKey = if (page == 1) null else page - 1
//                val nextKey = if (endOfPaginationReached) null else page + 1
//                val keys = response.data.map {
//                    RemoteKeysEntity(id = it.isbn, prevKey = prevKey, nextKey = nextKey)
//                }
//                database.remoteKeysDao().insertAll(keys)
//                val mappedData = response.data.map { item->
//                    BookEntity(
//                        coverUrl = item.coverUrl,
//                        publicationYear = item.publicationYear,
//                        author = item.author,
//                        subject = item.subject.joinToString(", "),
//                        isbn = item.isbn,
//                        publisher = item.publisher,
//                        description = item.description.joinToString(", "),
//                        title = item.title,
//                        idPerpus = item.idPerpus
//                    )
//                }
//
//                database.bookDao().insertBook(mappedData)
//
//            }
//            Log.d("bookremotemediatorijul", "bookremote : ${response.data.get(INITIAL_PAGE_INDEX).coverUrl}")
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//        } catch (exception: Exception) {
//            Log.d("bookremotemediatorijul", "data : $exception")
//            return MediatorResult.Error(exception)
//        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, BookEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, BookEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, BookEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.isbn?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }


}