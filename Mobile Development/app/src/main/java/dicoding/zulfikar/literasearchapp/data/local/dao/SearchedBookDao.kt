package dicoding.zulfikar.literasearchapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.SearchedBookEntity

@Dao
interface SearchedBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: List<SearchedBookEntity>)

    @Query("SELECT * FROM searched_book")
    fun getAllBook(): PagingSource<Int, SearchedBookEntity>

    @Query("DELETE FROM searched_book")
    suspend fun deleteAllBook()
}