package dicoding.zulfikar.literasearchapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.PopularBookEntity

@Dao
interface PopularDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: List<PopularBookEntity>)

    @Query("SELECT * FROM popular_book")
    fun getAllBook(): PagingSource<Int, PopularBookEntity>

    @Query("DELETE FROM popular_book")
    suspend fun deleteAllBook()
}

