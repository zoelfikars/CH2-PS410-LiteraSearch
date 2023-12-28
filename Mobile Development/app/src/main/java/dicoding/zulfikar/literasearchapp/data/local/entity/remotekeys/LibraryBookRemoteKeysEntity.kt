package dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "library_book_remote_keys")
data class LibraryBookRemoteKeysEntity(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)