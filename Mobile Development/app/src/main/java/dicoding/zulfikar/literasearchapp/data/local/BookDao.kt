package dicoding.zulfikar.literasearchapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(story: List<BookEntity>)

    @Query("SELECT * FROM book")
    fun getBookPaging(): PagingSource<Int, BookEntity>

    @Query("SELECT * FROM book")
    suspend fun getBook(): List<BookEntity>

    @Query("DELETE FROM book")
    suspend fun deleteAll()
}