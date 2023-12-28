package dicoding.zulfikar.literasearchapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity

@Dao
interface LibraryBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: List<LibraryBookEntity>)

    @Query("SELECT * FROM library_book")
    fun getAllBook(): PagingSource<Int, LibraryBookEntity>

    @Query("DELETE FROM library_book")
    suspend fun deleteAllBook()
}
