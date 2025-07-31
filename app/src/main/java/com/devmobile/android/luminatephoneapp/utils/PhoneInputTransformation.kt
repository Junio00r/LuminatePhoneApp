package com.devmobile.android.luminatephoneapp.utils

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert
import androidx.core.text.isDigitsOnly

class PhoneTransformation() {
    val region = PhoneSymbolConfiguration.american

    inner class PhoneInputTransformation() : InputTransformation {

        override fun TextFieldBuffer.transformInput() {
            if (!asCharSequence().isDigitsOnly()) {
                revertAllChanges()
            }
        }
    }

    inner class PhoneOutputTransformation() : OutputTransformation {
        val phonePositions = mutableListOf<Int>()

        override fun TextFieldBuffer.transformOutput() {
            val allPositionToInsert = region.positionsToInsert(length)

            allPositionToInsert.forEachIndexed { index, value ->
                insert(value, region.getPhoneSymbols().get(index))
            }

//            if (region.canInsertSymbol(length)) {
//                insert(length, region.getPhoneSymbols().last())
//            }

//                insert(region.findNextIndex(length), region.getNextSymbol())
        }

        fun getPhoneLength(): Int {
            return region.numberLength
        }
    }
}
