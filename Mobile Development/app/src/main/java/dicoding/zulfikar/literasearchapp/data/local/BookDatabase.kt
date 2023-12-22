package dicoding.zulfikar.literasearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.RemoteKeysEntity

@Database(
    entities = [BookEntity::class, RemoteKeysEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun remoteKeysDao(): RemoteKeysDao

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