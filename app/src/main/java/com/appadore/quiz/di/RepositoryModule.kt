package com.appadore.quiz.di

import com.appadore.quiz.repository.QuizRepository
import com.appadore.quiz.repository.QuizRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindMeetingRepository(impl: QuizRepositoryImpl): QuizRepository


}