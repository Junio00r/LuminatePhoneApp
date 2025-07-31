package com.devmobile.android.luminatephoneapp.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object PhoneSymbolConfiguration {
    const val OPEN_PARENTHESIS: String = "("
    const val CLOSE_PARENTHESIS: String = ")"
    const val HYPHEN: String = "-"
    const val SPACE: String = " "

    val american: PhoneRegionSymbols
        get() = PhoneRegionSymbols(
            numberLength = 11,
            mask = "99999 999 999",
            positionsWithSymbols = mapOf(
                5 to SPACE,
                9 to SPACE,
            )
        )

    class PhoneRegionSymbols(
        val numberLength: Int,
        val mask: String,
        private val positionsWithSymbols: Map<Int, String>
    ) {


//        fun canInsertSymbol(position: Int): Boolean {
//            return getSymbolsPositions().all { position > it}
//        }

        fun positionsToInsert(position: Int): List<Int> {
            return positionsWithSymbols.keys.filter {
                position > it
            }
        }

        fun getPhoneSymbols(): List<String> {
            return positionsWithSymbols.values.toList()
        }
    }

    class CustomVisualTransformation() : VisualTransformation {

        override fun filter(text: AnnotatedString): TransformedText {
            text.text
            return TransformedText(
                AnnotatedString(text = "anything"),
                offsetMapping = OffsetMapping.Identity
            )
        }
    }
}