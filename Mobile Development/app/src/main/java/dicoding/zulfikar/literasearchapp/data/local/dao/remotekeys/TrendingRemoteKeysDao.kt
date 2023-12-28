package dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.TrendingRemoteKeysEntity

@Dao
interface TrendingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TrendingRemoteKeysEntity>)
    @Query("SELECT * FROM trending_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): TrendingRemoteKeysEntity?
    @Query("DELETE FROM trending_remote_keys")
    suspend fun deleteRemoteKeys()
}