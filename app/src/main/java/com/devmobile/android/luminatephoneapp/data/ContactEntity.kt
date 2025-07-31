package com.devmobile.android.luminatephoneapp.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val photo: Bitmap? = null,
    val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    val email: String,
    val birthday: LocalDate,
    val address: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "is_blocked")  val isBlocked: Boolean = false,
    @ColumnInfo(name = "is_notification_enabled") var isNotificationEnabled: Boolean
)

@Fts4(contentEntity = ContactEntity::class)
@Entity(tableName = "contacts_fts")
data class ContactFtsEntity(
    val name: String
)