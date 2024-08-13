package com.personal.tmdb.core.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.personal.tmdb.core.data.local.AppDatabase
import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.util.C
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        val prepopulateCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val defaultPreferences = ContentValues().apply {
                    put("accessToken", "")
                    put("accountId", "")
                    put("sessionId", "")
                    put("language", "en")
                    put("corners", 18)
                    put("useCards", true)
                    put("showTitle", false)
                    put("showVoteAverage", false)
                }
                db.insert("preferencesentity", SQLiteDatabase.CONFLICT_REPLACE, defaultPreferences)
            }
        }
        return Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "app_database"
        ).fallbackToDestructiveMigration().addCallback(prepopulateCallback).build()
    }

    @Singleton
    @Provides
    fun providePreferencesDao(db: AppDatabase) = db.getPreferencesDao()

    @Singleton
    @Provides
    fun provideTmdbApi(): TmdbApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(C.TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TmdbApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenses(@ApplicationContext appContext: Context) = appContext.getSharedPreferences("localCache", Context.MODE_PRIVATE)
}