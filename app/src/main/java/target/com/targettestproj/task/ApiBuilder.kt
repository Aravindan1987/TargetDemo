package target.com.targettestproj.task

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import target.com.targettestproj.model.GitAccount

open class APIBuilder {
    private constructor()

    var apiService : APIServices? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gSon = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .build()

        apiService = retrofit.create(APIServices::class.java)
    }

    companion object {
        const val BASE_URL = "github-trending-api.now.sh"

        private var builder : APIBuilder? = null
        fun getInstance() : APIBuilder {
            if (builder == null) {
                builder = APIBuilder()
            }
            return builder!!
        }
    }
}

interface APIServices {

    @GET("/developers")
    fun getFoodItems(@Query("language") language : String,
                     @Query("since") since : String): Observable<List<GitAccount>>?
}