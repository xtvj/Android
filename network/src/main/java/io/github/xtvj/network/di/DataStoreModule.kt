package io.github.xtvj.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.xtvj.network.data.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun provideDataManager(@ApplicationContext appContext: Context): DataStoreManager {
        return DataStoreManager(appContext)
    }

}