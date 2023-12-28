package dicoding.zulfikar.literasearchapp.view.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.TrendingBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.TrendingRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)
class TrendingRemoteMediator(
    private val database: BookDatabase,
    private val apiService: ApiService,
) : RemoteMediator<Int, TrendingBookEntity>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TrendingBookEntity>
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
            val responseData = apiService.getTrendingBook("daily", page, state.config.pageSize)
            val endOfPaginationReached = responseData.data.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.trendingRemoteKeysDao().deleteRemoteKeys()
                    database.trendingDao().deleteAllBook()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.data.map {
                    TrendingRemoteKeysEntity(id = it.isbn, prevKey = prevKey, nextKey = nextKey)
                }
                database.trendingRemoteKeysDao().insertAll(keys)

                val converted: List<TrendingBookEntity> =
                    responseData.data.map { it.toTrendingBookEntity() }
                database.trendingDao().insertBook(converted)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TrendingBookEntity>): TrendingRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.trendingRemoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TrendingBookEntity>): TrendingRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.trendingRemoteKeysDao().getRemoteKeysId(data.isbn)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TrendingBookEntity>): TrendingRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.isbn?.let { isbn ->
                database.trendingRemoteKeysDao().getRemoteKeysId(isbn)
            }
        }
    }
}