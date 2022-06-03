package com.comanch.testovie_list.dependencyInjection

import android.content.Context
import com.comanch.testovie_list.dataBase.DataControl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {

        @Singleton
        @Provides
        fun provideDatabase (@ApplicationContext context: Context): DataControl {
            return DataControl.getInstance(context)
        }

        @Singleton
        @Provides
        fun provideTimeDataDao(database: DataControl) = database.starTrackDao
    }
}