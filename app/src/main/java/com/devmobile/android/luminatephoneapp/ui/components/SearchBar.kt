package com.devmobile.android.luminatephoneapp.ui.components

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devmobile.android.luminatephoneapp.R
import com.devmobile.android.luminatephoneapp.ui.theme.Black
import com.devmobile.android.luminatephoneapp.ui.theme.BlackTwo
import com.devmobile.android.luminatephoneapp.ui.theme.GrayOne

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    placeHolder: String = "Search",
    onSearch: (String) -> Unit,
    query: String = ""
) {
    var queryToSearch by rememberSaveable { mutableStateOf(query) }

    Box(
        Modifier
            .fillMaxWidth()
            .background(color = Color.Unspecified)
            .semantics { isTraversalGroup = true },
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = GrayOne, shape = RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                value = TextFieldValue(""),
                onValueChange = {

                },
                placeholder = placeHolder,
                textStyle = TextStyle(
                    textAlign = TextAlign.Start,
                    fontSize = 22.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {

                    }
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        tint = Black.copy(alpha = 0.95f),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(20.dp)
                    )
                },
                trailingIcon = null
            )
        }
    }
}