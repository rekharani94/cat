package me.intuit.cat.data.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.di.BaseUrl
import me.intuit.cat.data.utils.AppConstant.API_KEY
import me.intuit.cat.data.utils.CacheInterceptor
import me.intuit.cat.data.utils.DefaultNetworkHelper
import me.intuit.cat.data.utils.NetworkHelper
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://api.thecatapi.com/v1/"//"https://api.thecatapi.com/v1/breeds"//"https://api.thecatapi.com/v1/images/:image_id/breeds"//"https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
     fun getRetrofit(@ApplicationContext context: Context,@BaseUrl baseUrl: String,networkHelper: NetworkHelper, okHttpClient: OkHttpClient = getOkHttpClient(networkHelper=networkHelper, context = context)): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NetworkService::class.java)
    }

    @Singleton
    @Provides
     fun getOkHttpNetworkInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest =
                    chain.request().newBuilder().addHeader("X-Api-Key", API_KEY).build()
                return chain.proceed(newRequest)
            }
        }
    }
    @Singleton
    @Provides
     fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
     fun getOkHttpClient(
        okHttpLogger: HttpLoggingInterceptor = getHttpLogger(),
        okHttpNetworkInterceptor: Interceptor = getOkHttpNetworkInterceptor(),
       networkHelper: NetworkHelper,
        @ApplicationContext context: Context
        ): OkHttpClient {
        return OkHttpClient.Builder().cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
            .addInterceptor(okHttpLogger)
            .addInterceptor(okHttpNetworkInterceptor)
            .addInterceptor(Interceptor { chain ->
                val builder: Request.Builder = chain.request().newBuilder()
                if (!networkHelper.isNetworkConnected()) {
                    builder.cacheControl(CacheControl.FORCE_CACHE);
                }
                chain.proceed(builder.build());
            })
            .addNetworkInterceptor(
                CacheInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return DefaultNetworkHelper(context)
    }

    /*    @Provides
        @Singleton
        fun provideNetworkService(
            @BaseUrl baseUrl: String,
            gsonConverterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient
        ): NetworkService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(NetworkService::class.java)
        }*/


}