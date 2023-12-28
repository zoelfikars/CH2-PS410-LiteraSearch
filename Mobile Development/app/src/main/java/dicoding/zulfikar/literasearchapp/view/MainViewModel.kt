package dicoding.zulfikar.literasearchapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.PopularBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.SearchedBookEntity
import dicoding.zulfikar.literasearchapp.data.local.entity.TrendingBookEntity
import dicoding.zulfikar.literasearchapp.data.model.Library
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.data.repository.BookRepository

class MainViewModel(private val repository: BookRepository) : ViewModel() {
    val popular: LiveData<PagingData<PopularBookEntity>> = repository.getPopularPaging().cachedIn(viewModelScope)
    val book: LiveData<PagingData<BookEntity>> =
        repository.getBookPaging().cachedIn(viewModelScope)
    val trending: LiveData<PagingData<TrendingBookEntity>> =
        repository.getTrendingPaging().cachedIn(viewModelScope)

    fun getPagingLibraryBook(id: String): LiveData<PagingData<LibraryBookEntity>> {
        val libraryBook: LiveData<PagingData<LibraryBookEntity>> =
            repository.getPagingLibraryBook(id).cachedIn(viewModelScope)
        return libraryBook
    }
    fun searchBook(key: String): LiveData<PagingData<SearchedBookEntity>> {
        val search: LiveData<PagingData<SearchedBookEntity>> =
            repository.searchBook(key).cachedIn(viewModelScope)
        return search
    }

    fun getLibrary(): LiveData<Result<List<Library>>> {
        return repository.getLibrary()
    }

//    fun getPopular(): LiveData<Result<List<Book>>> {
//        return repository.getPopular()
//    }

//    fun getTrending(key: String): LiveData<Result<List<Book>>> {
//        return repository.getTrending(key)
//    }

//    fun getBook(): LiveData<Result<List<Book>>> {
//        return repository.getBook()
//    }

//    fun getLibraryWithId(id: List<Int>): LiveData<Result<List<Library>>> {
//        return repository.getLibraryWithId(id)
//    }

}