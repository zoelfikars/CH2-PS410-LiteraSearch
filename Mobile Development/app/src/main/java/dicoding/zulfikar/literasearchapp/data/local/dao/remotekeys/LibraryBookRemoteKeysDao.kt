package dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.LibraryBookRemoteKeysEntity

@Dao
interface LibraryBookRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LibraryBookRemoteKeysEntity>)
    @Query("SELECT * FROM library_book_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): LibraryBookRemoteKeysEntity?
    @Query("DELETE FROM library_book_remote_keys")
    suspend fun deleteRemoteKeys()
}