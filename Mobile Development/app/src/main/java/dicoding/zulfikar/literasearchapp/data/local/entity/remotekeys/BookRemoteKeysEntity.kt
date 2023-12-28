package dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_remote_keys")
data class BookRemoteKeysEntity(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
