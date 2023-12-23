package dicoding.zulfikar.literasearchapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.data.model.Library
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.data.repository.BookRepository

class MainViewModel(private val repository: BookRepository) : ViewModel(){
    private val _stories = MutableLiveData<List<BookEntity>>()
    val stories: LiveData<List<BookEntity>> get() = _stories


    fun getTrendingBook(): LiveData<PagingData<BookEntity>> {
        return repository.getBookTrending().cachedIn(viewModelScope)
    }

    suspend fun getBook(){
        return repository.getBook()
    }

    suspend fun getLibrary() : Result<List<Library>> {
        return repository.getLibrary()
    }
//    suspend fun getStoryLocation(token: String) :Result<StoryResponse>{
//        return repository.getStoryLocation(token)
//    }
//    suspend fun login(email: String, password: String): Result<LoginResponse> {
//        return repository.login(email, password)
//    }
//
//    suspend fun register(name: String, email: String, password: String): Result<MessageResponse> {
//        return repository.register(name, email, password)
//    }

}