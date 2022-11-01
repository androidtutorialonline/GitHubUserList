package com.app.gitHubUserList.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.app.gitHubUserList.BuildConfig
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://api.github.com"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        val moshiBuilder = Moshi.Builder() .add(KotlinJsonAdapterFactory()) .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            // Moshi maps JSON to classes
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .build()
            .create(api)
    }

    private fun getRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}