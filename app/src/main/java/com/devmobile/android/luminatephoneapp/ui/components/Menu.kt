package com.devmobile.android.luminatephoneapp.ui.components

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.devmobile.android.luminatephoneapp.R

data class MenuItem(val startIcon: Icon? = null, val name: String)

@Composable
fun SimpleMenu(
    modifier: Modifier = Modifier,
    menuIcon: Painter,
    contentDescription: String = stringResource(R.string.menu_options),
    menusItems: List<MenuItem>,
    onMenuClick: (MenuItem) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val stableItems = remember(menusItems) { menusItems.toList() }

    Box {

        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.background(color = Color.Transparent, shape = CircleShape)
        ) {
            Icon(
                modifier = modifier.matchParentSize(),
                painter = menuIcon,
                contentDescription = contentDescription,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
        ) {
            stableItems.forEach { menuItem ->
                DropdownMenuItem(
                    text = { Text(text = menuItem.name) },
                    trailingIcon = { menuItem.startIcon },
                    onClick = { onMenuClick(menuItem) }
                )
            }
        }
    }
}