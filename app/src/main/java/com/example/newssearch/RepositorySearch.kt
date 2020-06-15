package com.example.newssearch

import com.example.newssearch.constant.const.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RepositorySearch {
    companion object {
        private var retrofit: Retrofit? = null
        private val logger = HttpLoggingInterceptor()


        private val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        private var builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client)
        private val httpClient = OkHttpClient.Builder()
        fun <S> createService(serviceClass: Class<S>): S {
            return null.createService(serviceClass)
        }

        private fun <S> Map<String, String>?.createService(
            serviceClass: Class<S>
        ): S {

            if (this != null) {
                val interceptor = AuthenticationInterceptor(authToken = this)
                if (!httpClient.interceptors().contains(interceptor)) {
                    httpClient.addInterceptor(interceptor)
                    builder.client(httpClient.build())
                    retrofit = builder.build()
                }
            }
            retrofit = builder.build()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            return retrofit!!.create(serviceClass)
        }

    }

    class AuthenticationInterceptor(private val authToken: Map<String, String>) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val builder = original.newBuilder()
            for (key in authToken.keys) {
                builder.header(key, authToken.getValue(key))
            }
            val request = builder.build()
            return chain.proceed(request)
        }
    }
}