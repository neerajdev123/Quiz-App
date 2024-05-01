package com.appadore.quiz.di

import com.appadore.quiz.data.StaticQuestionStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideStaticDataSource(): StaticQuestionStore {
        return StaticQuestionStore()
    }

}