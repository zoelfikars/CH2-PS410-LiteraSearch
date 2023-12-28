package dicoding.zulfikar.literasearchapp.view.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.LibraryBookRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)
class LibraryBookRemoteMediator(
    private val database: BookDatabase,
    private val apiService: ApiService,
    private val id: String,
) :
    RemoteMediator<Int, LibraryBookEntity>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LibraryBookEntity>
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
            val responseData = apiService.getBookByLibraryId(id, page, state.config.pageSize)
            Log.d("ijul", "respondata : $responseData")
            val endOfPaginationReached = responseData.data.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.libraryBookRemoteKeysDao().deleteRemoteKeys()
                    database.libraryBookDao().deleteAllBook()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d("ijul", "masuk transaksi key : $prevKey $nextKey")
                val keys = responseData.data.map {
                    LibraryBookRemoteKeysEntity(id = it.isbn, prevKey = prevKey, nextKey = nextKey)
                }
                database.libraryBookRemoteKeysDao().insertAll(keys)
                val converted: List<LibraryBookEntity> =
                    responseData.data.map { it.toLibraryBookEntity() }
                database.libraryBookDao().insertBook(converted)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LibraryBookEntity>): LibraryBookRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.libraryBookRemoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LibraryBookEntity>): LibraryBookRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.libraryBookRemoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, LibraryBookEntity>): LibraryBookRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.isbn?.let { isbn ->
                database.libraryBookRemoteKeysDao().getRemoteKeysId(isbn)
            }
        }
    }
}