package com.devmobile.android.luminatephoneapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.luminatephoneapp.data.CallEntity

@Dao
interface CallDao {

    @Insert
    suspend fun add(callEntity: CallEntity)

    @Delete
    suspend fun delete(vararg callEntities: CallEntity)

    @Update
    suspend fun update(vararg callEntities: CallEntity)

    @Query("SELECT * FROM calls")
    suspend fun fetchAll(): List<CallEntity>

    @Query("SELECT * FROM calls WHERE :fromContactId = id")
    suspend fun fetchById(fromContactId: Int): List<CallEntity>

//    @Query("SELECT * FROM calls WHERE :query = status")
//    suspend fun pagingSource(query: String): PagingSource<Int, CallEntity>
}