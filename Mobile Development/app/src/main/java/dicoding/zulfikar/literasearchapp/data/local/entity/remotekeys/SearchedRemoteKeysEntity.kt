package dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_remote_keys")
data class SearchedRemoteKeysEntity(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
