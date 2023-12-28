package dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.PopularRemoteKeysEntity

@Dao
interface PopularRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PopularRemoteKeysEntity>)
    @Query("SELECT * FROM popular_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): PopularRemoteKeysEntity?
    @Query("DELETE FROM popular_remote_keys")
    suspend fun deleteRemoteKeys()
}