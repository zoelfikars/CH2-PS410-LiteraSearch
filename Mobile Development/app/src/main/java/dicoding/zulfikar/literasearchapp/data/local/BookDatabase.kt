package dicoding.zulfikar.literasearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dicoding.zulfikar.literasearchapp.data.local.dao.BookDao
import dicoding.zulfikar.literasearchapp.data.local.dao.LibraryBookDao
import dicoding.zulfikar.literasearchapp.data.local.dao.PopularDao
import dicoding.zulfikar.literasearchapp.data.local.dao.SearchedBookDao
import dicoding.zulfikar.literasearchapp.data.local.dao.TrendingDao
import dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys.BookRemoteKeysDao
import dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys.LibraryBookRemoteKeysDao
import dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys.PopularRemoteKeysDao
import dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys.SearchedRemoteKeysDao
import dicoding.zulfikar.literasearchapp.data.local.dao.remotekeys.TrendingRemoteKeysDao
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.PopularBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.SearchedBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.TrendingBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.BookRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.LibraryBookRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.PopularRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.SearchedRemoteKeysEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.remotekeys.TrendingRemoteKeysEntity

@Database(
    entities = [
        BookEntity::class,
        PopularBookEntity::class,
        TrendingBookEntity::class,
        SearchedBookEntity::class,
        BookRemoteKeysEntity::class,
        PopularRemoteKeysEntity::class,
        TrendingRemoteKeysEntity::class,
        SearchedRemoteKeysEntity::class,
        LibraryBookEntity::class,
        LibraryBookRemoteKeysEntity::class,
    ],
    version = 4,
    exportSchema = false
)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookRemoteKeysDao(): BookRemoteKeysDao
    abstract fun popularDao(): PopularDao
    abstract fun popularRemoteKeysDao(): PopularRemoteKeysDao
    abstract fun trendingDao(): TrendingDao
    abstract fun trendingRemoteKeysDao(): TrendingRemoteKeysDao
    abstract fun searchDao(): SearchedBookDao
    abstract fun searchRemoteKeysDao(): SearchedRemoteKeysDao
    abstract fun libraryBookDao(): LibraryBookDao
    abstract fun libraryBookRemoteKeysDao(): LibraryBookRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java, "book_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}