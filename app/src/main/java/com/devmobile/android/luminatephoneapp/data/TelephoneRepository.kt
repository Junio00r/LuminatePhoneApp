package com.devmobile.android.luminatephoneapp.data

import com.devmobile.android.luminatephoneapp.data.local.AppDatabaseLocalDataSource
import com.devmobile.android.luminatephoneapp.di.AppScope
import com.devmobile.android.luminatephoneapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TelephoneRepository @Inject constructor(
    private val localDatabase: AppDatabaseLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @AppScope private val coroutineScope: CoroutineScope
) {

    private val contactDao = localDatabase.getContactDao()
    private val callDao = localDatabase.getCallDao()

    suspend fun fetchContacts(contactId: Int? = null) = withContext(dispatcher) {

        return@withContext if (contactId == null) {
            contactDao.fetchAllContacts()
        } else {
            contactDao.fetchById(contactId)
        }
    }

    suspend fun fetchCalls(callId: String? = null): List<CallEntity> {
        return withContext(dispatcher) {
            return@withContext if (callId == null) {
                callDao.fetchAll()
            } else {
                callDao.fetchAll()
            }
        }
    }
}