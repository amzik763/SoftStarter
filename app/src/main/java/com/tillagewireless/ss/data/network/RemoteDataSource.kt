package com.tillagewireless.ss.data.network

import android.content.Context
//import androidx.viewbinding.BuildConfig
import com.tillagewireless.ss.others.Constants.VSU_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context,
        token: String? = null
    ): Api {
        TokenAuthenticator(context, buildTokenApi())
        return Retrofit.Builder()
            .baseUrl(VSU_BASE_URL)
            .client(getRetrofitClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun buildTokenApi(): TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl(VSU_BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenRefreshApi::class.java)
    }
    private fun getRetrofitClient(/*authenticator: Authenticator? = null,*/ token: String?=null): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                    if(token != null) {
                        it.addHeader("Authorization", "Bearer $token")
                    }
                }.build())
            }.also { client ->
               // authenticator?.let { client.authenticator(it) }
//                if (BuildConfig.DEBUG) {
//                    val logging = HttpLoggingInterceptor()
//                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//                    client.addInterceptor(logging)
//                }
            }.build()
    }
}