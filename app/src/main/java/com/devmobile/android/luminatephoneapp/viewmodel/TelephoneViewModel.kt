package com.devmobile.android.luminatephoneapp.viewmodel

import androidx.lifecycle.ViewModel
import com.devmobile.android.luminatephoneapp.data.CallEntity
import com.devmobile.android.luminatephoneapp.data.ContactEntity
import com.devmobile.android.luminatephoneapp.data.TelephoneRepository
import com.devmobile.android.luminatephoneapp.di.DefaultDispatcher
import com.devmobile.android.luminatephoneapp.di.ViewModelScope
import com.devmobile.android.luminatephoneapp.utils.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelephoneViewModel @Inject constructor(
    private val repository: TelephoneRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ViewModelScope private val scope: CoroutineScope
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _contacts = MutableSharedFlow<List<ContactEntity>>()
    val contacts: Flow<List<ContactEntity>> = flow {
        when (_currentFilterApplied.value?.filterName) {

            "Favorites" -> {

                val contactsFiltered = _contacts.single().filter { it.isFavorite }
                _contacts.emit(contactsFiltered)
            }

            "Blocked" -> {
                val contactsFiltered = _contacts.single().filter { it.isFavorite }
                _contacts.emit(contactsFiltered)
            }

            else -> {
                _contacts.emit(_contacts.single())
            }
        }
    }
    private val _calls = MutableSharedFlow<List<CallEntity>>()

    val calls: Flow<List<CallEntity>> = flow {
        when (_currentFilterApplied.value?.filterName) {
            "Incoming" -> {

                val callsFiltered = _calls.single().filter { it.status == "Incoming" }
                _calls.emit(callsFiltered)
            }

            "Missed" -> {
                val callsFiltered = _calls.single().filter { it.status == "Missed" }
                _calls.emit(callsFiltered)
            }

            "Outgoing" -> {
                val callsFiltered = _calls.single().filter { it.status == "Outgoing" }
                _calls.emit(callsFiltered)
            }

            "Blocked" -> {
                val callsFiltered = _calls.single().filter { it.status == "Blocked" }
                _calls.emit(callsFiltered)
            }

            else -> {
                _calls.emit(_calls.last())
            }
        }
    }
    private val _filters = MutableStateFlow(
        listOf(
            Filter("Incoming"),
            Filter("Missed"),
            Filter("Outgoing"),
            Filter("Blocked"),
        )
    )
    val filters = _filters.asStateFlow()
    private val _currentFilterApplied = MutableStateFlow<Filter?>(null)

    fun onPhoneNumberChange(phoneNumber: String) {

    }

    fun searchContacts(contactId: Int? = null) {
        scope.launch {
            repository.fetchContacts(contactId)
        }
    }

    fun searchCalls(callId: String? = null) {
        scope.launch {
            val newCalls = repository.fetchCalls(callId)

            if (newCalls.isNotEmpty()) {
                _calls.emit(newCalls)
            }
        }
    }

    fun onFilter(filter: Filter) {

        _filters.value = _filters.value.map {
            if (it.isSelected) it.copy(isSelected = false)
            else if (it.filterName == filter.filterName) it.copy(isSelected = true)
            else it
        }
        _currentFilterApplied.value = _filters.value.find { it.isSelected }
    }
}
