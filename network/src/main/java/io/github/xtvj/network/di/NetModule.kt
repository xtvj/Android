package io.github.xtvj.network.di

import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.xtvj.network.BuildConfig
import io.github.xtvj.network.api.ApiService
import io.github.xtvj.network.common.ConstantUtils
import io.github.xtvj.network.data.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Retention(AnnotationRetention.BINARY)
@Qualifier
@MustBeDocumented
annotation class BaseUrl

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @Singleton
    fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun client(
        dataStoreManager: DataStoreManager,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            //日志拦截器会导致body的writeTo方法执行两次
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val token = runBlocking {
                    dataStoreManager.fetchInitialPreferences().token
                }
                val request = if (token.isBlank()) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", ConstantUtils.authorization)
                        .build()
                } else {
                    chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build()
                }
                chain.proceed(request)
            }
            .build()
    }


    @Provides
    @BaseUrl
    fun baseUrl(): String = ConstantUtils.getBaseUrl()

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient, @BaseUrl baseUrl: String, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()


}