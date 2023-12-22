package dicoding.zulfikar.literasearchapp.data.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
//        val authInterceptor = Interceptor { chain ->
//            val req = chain.request()
//            val requestHeaders = req.newBuilder()
//                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWxBeUVvaWRPQ3Nrby0wXzgiLCJpYXQiOjE3MDI2NTU2MDd9.npzFS0ucT0sFucdvCY0ff61TzJKVHNMmPPunrqPk9tg")
//                .build()
//            chain.proceed(requestHeaders)
//        }
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cc-api-dot-literasearch-405711.et.r.appspot.com/api/books/")
//            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}