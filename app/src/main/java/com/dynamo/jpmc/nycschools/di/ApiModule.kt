package com.dynamo.jpmc.nycschools.di

import android.content.Context
import androidx.room.Room
import com.dynamo.jpmc.nycschools.data.local.SatScoreDao
import com.dynamo.jpmc.nycschools.data.local.SchoolDao
import com.dynamo.jpmc.nycschools.data.local.SchoolDatabase
import com.dynamo.jpmc.nycschools.util.ApiConstants
import com.dynamo.jpmc.nycschools.util.SchoolApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by tanmaythakar on 3/17/23.
 */

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder, okHttpClient: OkHttpClient): SchoolApi {
        return builder.client(okHttpClient).build().create(SchoolApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideSchoolDatabase(@ApplicationContext context: Context): SchoolDatabase {
        return Room.databaseBuilder(
            context,
            SchoolDatabase::class.java,
            "school_db.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSchoolDao(schoolDatabase: SchoolDatabase): SchoolDao {
        return schoolDatabase.schoolDao()
    }

    @Singleton
    @Provides
    fun provideSatScoreDao(schoolDatabase: SchoolDatabase): SatScoreDao {
        return schoolDatabase.satScoreDao()
    }
}