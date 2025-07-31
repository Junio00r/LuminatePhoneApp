package com.devmobile.android.luminatephoneapp.data

import com.devmobile.android.luminatephoneapp.di.IoDispatcher
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
class DataStoreDataSource @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
) {
}