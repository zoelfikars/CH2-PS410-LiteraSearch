package dicoding.zulfikar.literasearchapp.data.di

import android.content.Context
import dicoding.zulfikar.literasearchapp.data.local.BookDatabase
import dicoding.zulfikar.literasearchapp.data.remote.retrofit.ApiConfig
import dicoding.zulfikar.literasearchapp.data.repository.BookRepository

object Injection {
    fun provideRepository(context: Context): BookRepository {
        val bookDatabase = BookDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return BookRepository.getInstance(apiService, bookDatabase
        )
    }
}