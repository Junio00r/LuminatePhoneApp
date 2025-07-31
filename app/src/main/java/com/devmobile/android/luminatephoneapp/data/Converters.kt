package com.devmobile.android.luminatephoneapp.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Canvas
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
object Converters {

    @TypeConverter
    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        bitmap.recycle()

        return byteArray
    }

    @TypeConverter
    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) return null

        val options = BitmapFactory.Options().apply { inMutable = true }
        val btm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

        return btm
    }

    @TypeConverter
    fun localDataToDate(date: LocalDate?) : String? {
        if(date == null) return null

        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun stringToLocalDate(string: String?): LocalDate? {
        if(string == null) return null

        return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
    }
 }