package com.satverse.bookbox.di

import android.content.Context
import com.satverse.bookbox.database.BookboxDatabase
import com.satverse.bookbox.others.BookDownloader
import com.satverse.bookbox.others.WelcomeDataStore
import com.satverse.bookbox.repo.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class MainModule {

    @Provides
    fun provideAppContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideBookboxDatabase(@ApplicationContext context: Context) =
        BookboxDatabase.getInstance(context)

    @Provides
    fun provideLibraryDao(bookboxDatabase: BookboxDatabase) = bookboxDatabase.getLibraryDao()

    @Provides
    fun provideReaderDao(bookboxDatabase: BookboxDatabase) = bookboxDatabase.getReaderDao()

    @Singleton
    @Provides
    fun provideBooksApi() = BookRepository()

    @Singleton
    @Provides
    fun provideBookDownloader(@ApplicationContext context: Context) = BookDownloader(context)

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = WelcomeDataStore(context = context)
}