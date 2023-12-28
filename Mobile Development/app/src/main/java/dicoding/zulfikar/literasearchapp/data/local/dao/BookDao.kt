package dicoding.zulfikar.literasearchapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: List<BookEntity>)

    @Query("SELECT * FROM all_book")
    fun getAllBook(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM all_book")
    suspend fun deleteAllBook()
}