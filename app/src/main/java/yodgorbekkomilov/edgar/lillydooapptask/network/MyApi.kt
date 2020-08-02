package yodgorbekkomilov.edgar.lillydooapptask.network

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import yodgorbekkomilov.edgar.lillydooapptask.ModelUser
import yodgorbekkomilov.edgar.lillydooapptask.ModelLogin
import java.net.CookieHandler
import java.net.CookieManager

interface MyApi {

    @POST(TOKEN)
    fun token(@Body body: HashMap<String, String>): Call<ModelLogin>

    @GET(LOGIN)
    fun login(): Call<ModelUser>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val cookieHandler: CookieHandler = CookieManager()

            val okHttpClient = OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(cookieHandler))
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}