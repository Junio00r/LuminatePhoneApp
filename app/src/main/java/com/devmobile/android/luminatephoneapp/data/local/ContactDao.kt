package com.devmobile.android.luminatephoneapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.luminatephoneapp.data.CallEntity
import com.devmobile.android.luminatephoneapp.data.ContactEntity

@Dao
interface ContactDao {

    @Insert
    suspend fun add(vararg contactEntity: ContactEntity)

    @Delete
    suspend fun delete(contactEntity: ContactEntity)

    @Update
    suspend fun update(vararg contactEntity: ContactEntity)

    @Query(
        """SELECT contacts.* 
            FROM contacts 
            JOIN contacts_fts 
            ON contacts.name = contacts_fts.name 
            WHERE contacts_fts.name 
            MATCH :name"""
    )
    suspend fun fetchByName(name: String? = ""): List<ContactEntity>

    @Query("SELECT * FROM contacts WHERE :id = id")
    suspend fun fetchById(id: Int): List<ContactEntity>

    @Query("SELECT * FROM contacts")
    suspend fun fetchAllContacts(): List<ContactEntity>

    @Query("SELECT * FROM contacts JOIN calls ON contacts.id = calls.contact_id")
    suspend fun loadContactAndCalls(): Map<ContactEntity, List<CallEntity>>

//    @Query("SELECT * FROM contacts WHERE :query = name")
//    suspend fun pagingSource(query: String): PagingSource<Int, ContactEntity>
}