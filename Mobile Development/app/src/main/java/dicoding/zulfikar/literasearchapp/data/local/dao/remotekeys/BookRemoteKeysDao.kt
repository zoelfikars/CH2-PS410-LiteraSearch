package dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.BookRemoteKeysEntity

@Dao
interface BookRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<BookRemoteKeysEntity>)
    @Query("SELECT * FROM book_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): BookRemoteKeysEntity?
    @Query("DELETE FROM book_remote_keys")
    suspend fun deleteRemoteKeys()
}