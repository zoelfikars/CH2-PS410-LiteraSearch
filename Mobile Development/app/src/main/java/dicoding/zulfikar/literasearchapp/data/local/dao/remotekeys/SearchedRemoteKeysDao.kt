package dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.SearchedRemoteKeysEntity

@Dao
interface SearchedRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<SearchedRemoteKeysEntity>)
    @Query("SELECT * FROM searched_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): SearchedRemoteKeysEntity?
    @Query("DELETE FROM searched_remote_keys")
    suspend fun deleteRemoteKeys()
}