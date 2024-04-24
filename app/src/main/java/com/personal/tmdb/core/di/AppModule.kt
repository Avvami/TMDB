package com.personal.tmdb.core.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.personal.tmdb.core.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
                    put("isDark", false)
                    put("sessionId", "")
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
}