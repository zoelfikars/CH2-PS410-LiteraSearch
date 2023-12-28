package dicoding.zulfikar.literasearchapp.view.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.BookRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)

class BookRemoteMediator(
    private val database: BookDatabase,
    private val apiService: ApiService,
) :
    RemoteMediator<Int, BookEntity>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
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
        try {
            val responseData = apiService.getAllBook(page, state.config.pageSize)
            val endOfPaginationReached = responseData.data.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.bookRemoteKeysDao().deleteRemoteKeys()
                    database.bookDao().deleteAllBook()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.data.map {
                    BookRemoteKeysEntity(id = it.isbn, prevKey = prevKey, nextKey = nextKey)
                }
                database.bookRemoteKeysDao().insertAll(keys)
                val converted: List<BookEntity> =
                    responseData.data.map { it.toBookEntity() }
                database.bookDao().insertBook(converted)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, BookEntity>): BookRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.bookRemoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, BookEntity>): BookRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.bookRemoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, BookEntity>): BookRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.isbn?.let { isbn ->
                database.bookRemoteKeysDao().getRemoteKeysId(isbn)
            }
        }
    }
}