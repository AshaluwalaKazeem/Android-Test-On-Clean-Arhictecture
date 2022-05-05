package com.example.punchandroidtest.di

import android.content.Context
import androidx.room.Room
import com.example.punchandroidtest.common.Constants
import com.example.punchandroidtest.data.db.MarsDao
import com.example.punchandroidtest.data.db.MarsDatabase
import com.example.punchandroidtest.data.remote.MarsServerApi
import com.example.punchandroidtest.data.repository.MarsRepositoryImpl
import com.example.punchandroidtest.domain.repository.MarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarsApi() : MarsServerApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarsServerApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMarsDb(@ApplicationContext context: Context) : MarsDatabase {
        return Room.databaseBuilder(
            context,
            MarsDatabase::class.java,
            MarsDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideMarsDAO(marsDatabase: MarsDatabase): MarsDao {
        return marsDatabase.marsDao()
    }


    @Provides
    @Singleton
    fun provideMarsRepository(api: MarsServerApi, marsDao: MarsDao): MarsRepository {
        return MarsRepositoryImpl(api, marsDao)
    }
}