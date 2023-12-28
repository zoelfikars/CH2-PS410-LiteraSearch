package dicoding.zulfikar.literasearchapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.TrendingBookEntity

@Dao
interface TrendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: List<TrendingBookEntity>)

    @Query("SELECT * FROM trending_book")
    fun getAllBook(): PagingSource<Int, TrendingBookEntity>

    @Query("DELETE FROM trending_book")
    suspend fun deleteAllBook()
}