package com.devmobile.android.luminatephoneapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(
    tableName = "calls",
    foreignKeys = [
        ForeignKey(
            entity = ContactEntity::class,
            parentColumns = ["id"],
            childColumns = ["contact_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class CallEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "contact_id") val callOfContactId: Int,
    val status: String,
    val time: String
)

@Fts4(contentEntity = CallEntity::class)
@Entity(tableName = "call_fts")
data class CallFtsEntity(
    val status: String
)
