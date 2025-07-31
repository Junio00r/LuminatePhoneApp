package com.devmobile.android.luminatephoneapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.devmobile.android.luminatephoneapp.R
import com.devmobile.android.luminatephoneapp.ui.components.CustomTextField
import com.devmobile.android.luminatephoneapp.ui.theme.BlackTwo
import com.devmobile.android.luminatephoneapp.ui.theme.GrayOne
import com.devmobile.android.luminatephoneapp.ui.theme.GreenLight
import com.devmobile.android.luminatephoneapp.ui.theme.GreenLightThree
import com.devmobile.android.luminatephoneapp.ui.theme.Typography
import com.devmobile.android.luminatephoneapp.utils.PhoneSymbolConfiguration
import com.devmobile.android.luminatephoneapp.viewmodel.TelephoneViewModel

@Preview
@Composable
fun KeypadScreen(viewModel: TelephoneViewModel = hiltViewModel()) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()

    KeypadScreenContent(phoneNumber) { phoneNumberChanged ->
        viewModel.searchContacts()
        viewModel.onPhoneNumberChange(phoneNumberChanged)
    }
}

@Composable
fun KeypadScreenContent(newPhoneNumber: String, onChangeNumber: (String) -> Unit) {

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        var phoneNumber by remember {
            mutableStateOf(TextFieldValue(newPhoneNumber))
        }

        // 2) PhoneNumber and contact name
        CustomTextField(
            modifier = Modifier
                .focusProperties { canFocus = false }
                .padding(top = 48.dp, bottom = 32.dp, start = 16.dp, end = 16.dp),
            value = phoneNumber,
            onValueChange = { newValue ->
                onChangeNumber(newValue.text)
            },
            placeholder = PhoneSymbolConfiguration.american.mask,
            textStyle = Typography.titleLarge.copy(
                textAlign = TextAlign.Center, color = BlackTwo
            ),
        )

        // 3) Keypad
        Keypad { keyAction ->

            when (keyAction) {

                is KeyAction.UpdateNumber -> {
                    val newValue = phoneNumber.text + keyAction.value

                    phoneNumber = phoneNumber.copy(
                        text = phoneNumber.text + keyAction.value,
                        selection = TextRange(newValue.length)
                    )
                }

                is KeyAction.Call -> {

                }

                is KeyAction.Exclude -> {

                    if (phoneNumber.text.isNotEmpty()) {

                        if (keyAction.all) {

                            phoneNumber = phoneNumber.copy(text = "")

                        } else {

                            val newText = phoneNumber.text.dropLast(1)
                            phoneNumber = phoneNumber.copy(
                                text = newText,
                                selection = TextRange(phoneNumber.selection.end - 1)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Keypad(modifier: Modifier = Modifier, onAction: (KeyAction) -> Unit) {
    val numbers = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9"),
        arrayOf("*", "0", "#")
    )
    val secondaryKeys = arrayOf(
        arrayOf("", "ABC", "DEF"),
        arrayOf("GHI", "JKL", "MNO"),
        arrayOf("PQRS", "TUV", "WXYZ"),
        arrayOf("", "+", "")
    )

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        numbers.forEachIndexed { rowIndex, rowKeys ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowKeys.forEachIndexed { colIndex, key ->
                    CharButton(
                        keyOne = key,
                        keyTwo = secondaryKeys[rowIndex][colIndex],
                        onClick = {
                            onAction(KeyAction.UpdateNumber(key))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            CallButton(
                onClick = { /* place call */ })

            Spacer(
                Modifier.width(34.dp)
            )

            ExcludeButton(
                modifier = Modifier,
                onExclude = {
                    onAction(
                        KeyAction.Exclude()
                    )
                },
                onExcludeAll = {

                    onAction(
                        KeyAction.Exclude(true)
                    )
                },
            )
        }
    }
}

@Composable
fun CharButton(keyOne: String, keyTwo: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .size(76.dp)
            .background(color = Color.White, shape = CircleShape),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = GrayOne, contentColor = BlackTwo
        )
    ) {

        Column(Modifier.fillMaxSize(), Arrangement.Center) {

            if (keyOne.isDigitsOnly()) {

                Text(
                    text = keyOne,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = keyTwo,
                    color = BlackTwo,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = keyOne,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun CallButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .size(76.dp)
            .background(
                color = Color.Green,
                shape = CircleShape,
            ), colors = ButtonDefaults.buttonColors().copy(
            containerColor = GreenLightThree,
            contentColor = GreenLight,
        )
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(R.drawable.call),
            contentDescription = "Call",
            tint = Color.White
        )
    }
}

@Composable
fun ExcludeButton(modifier: Modifier = Modifier, onExclude: () -> Unit, onExcludeAll: () -> Unit) {

    Surface(
        modifier = modifier
            .size(76.dp)
            .clip(CircleShape)
            .combinedClickable(
                onClick = onExclude, onLongClick = onExcludeAll
            ), color = GrayOne
    ) {
        Icon(
            modifier = Modifier.padding(20.dp),
            painter = painterResource(R.drawable.ic_backspace),
            contentDescription = "Exclude Key",
            tint = BlackTwo
        )
    }
}

@Preview
@Composable
fun Preview() {
    KeypadScreenContent("") { }
}

sealed class KeyAction() {
    data class UpdateNumber(val value: String) : KeyAction()
    data class Call(val canCall: Boolean = true, val action: () -> Unit) : KeyAction()
    data class Exclude(val all: Boolean = false) : KeyAction()
}
