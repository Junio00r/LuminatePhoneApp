package com.devmobile.android.luminatephoneapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devmobile.android.luminatephoneapp.data.CallEntity
import com.devmobile.android.luminatephoneapp.data.CallFtsEntity
import com.devmobile.android.luminatephoneapp.data.ContactEntity
import com.devmobile.android.luminatephoneapp.data.ContactFtsEntity
import com.devmobile.android.luminatephoneapp.data.Converters
import com.devmobile.android.luminatephoneapp.data.daos.CallDao
import com.devmobile.android.luminatephoneapp.data.daos.ContactDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.concurrent.Volatile

@Database(entities = [CallEntity::class, CallFtsEntity::class, ContactEntity::class, ContactFtsEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabaseLocalDataSource : RoomDatabase() {

    abstract fun getCallDao(): CallDao

    abstract fun getContactDao(): ContactDao

    companion object {
        @Volatile
        private var instance: AppDatabaseLocalDataSource? = null

        fun getInstance(@ApplicationContext context: Context): AppDatabaseLocalDataSource {

            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabaseLocalDataSource::class.java,
                    "AppLocalDatabase"
                )
                .build()
                .also { instance = it }
            }
        }
    }
}