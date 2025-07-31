package com.devmobile.android.luminatephoneapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devmobile.android.luminatephoneapp.R
import com.devmobile.android.luminatephoneapp.ui.components.MenuItem
import com.devmobile.android.luminatephoneapp.ui.components.SimpleMenu
import com.devmobile.android.luminatephoneapp.ui.theme.BlackTwo
import com.devmobile.android.luminatephoneapp.ui.theme.GrayLightOne
import com.devmobile.android.luminatephoneapp.ui.theme.GrayOne
import com.devmobile.android.luminatephoneapp.ui.theme.White
import com.devmobile.android.luminatephoneapp.data.ContactEntity
import com.devmobile.android.luminatephoneapp.viewmodel.TelephoneViewModel
import java.time.LocalDate

data class ContactData(val name: String, val number: String, val email: String)

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    contactId: Int? = null,
    viewModel: TelephoneViewModel = hiltViewModel(),
    onNavigationUp: () -> Unit,
    onShareContact: (ContactData) -> Unit
) {
    val contactEntity = ContactEntity(
        0,
        photo = null,
        name = "Alex",
        phoneNumber = "+1 (170) 305-9410",
        email = "alex.johnson89@mail.com",
        birthday = LocalDate.of(1995, 3, 14),
        address = "Adress 13 Elm Street, Springfield, IL 62704, USA",
        isFavorite = true,
        isNotificationEnabled = false
    )
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = contactEntity.name,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.figtree_medium)),
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier,
                        onClick = onNavigationUp
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(R.drawable.ic_navigation),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = White
                ),
                actions = {
                    IconButton(
                        onClick = {
                            val contact =
                                ContactData(contactEntity.name, contactEntity.phoneNumber, contactEntity.email)
                            onShareContact(contact)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            painter = painterResource(R.drawable.ic_share),
                            contentDescription = null
                        )
                    }
                    SimpleMenu(
                        modifier = Modifier.padding(8.dp),
                        menuIcon = painterResource(R.drawable.ic_options),
                        menusItems = listOf(MenuItem(name = "Menu 1")),
                        onMenuClick = { Log.d("DEBUGGING", "Clicked") }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CustomButtonMedia(
                    modifier = Modifier.size(128.dp),
                    mainVisualMedia = painterResource(R.drawable.image_contact),
                    padding = PaddingValues(0.dp),
                )

                Column(
                    Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = contactEntity.name,
                        fontSize = 28.sp,
                        fontFamily = FontFamily(Font(R.font.figtree_medium)),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = contactEntity.phoneNumber,
                        color = BlackTwo,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.figtree_regular)),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {

                    CustomButtonMedia(
                        mainVisualMedia = painterResource(R.drawable.ic_telephone),
                        modifier = Modifier
                            .size(60.dp),
                        padding = PaddingValues(16.dp),
                    )
                    CustomButtonMedia(
                        mainVisualMedia = painterResource(R.drawable.ic_call_video),
                        modifier = Modifier
                            .size(60.dp),
                        padding = PaddingValues(16.dp),
                    )
                    CustomButtonMedia(
                        mainVisualMedia = painterResource(R.drawable.ic_message),
                        modifier = Modifier
                            .size(60.dp),
                        padding = PaddingValues(16.dp),
                    )
                    CustomButtonMedia(
                        mainVisualMedia = if (contactEntity.isNotificationEnabled) painterResource(R.drawable.ic_enabled_notifications) else painterResource(
                            R.drawable.ic_disabled_notifications
                        ),
                        alternativeVisualMedia = if (!contactEntity.isNotificationEnabled) painterResource(
                            R.drawable.ic_enabled_notifications
                        ) else painterResource(R.drawable.ic_disabled_notifications),
                        modifier = Modifier
                            .size(60.dp),
                        padding = PaddingValues(16.dp),
                        onClick = { contactEntity.isNotificationEnabled = !contactEntity.isNotificationEnabled }
                    )
                }
            }
            Spacer(
                Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )

            val userMetadataList = listOf(
                Item(
                    visualMedia = painterResource(R.drawable.ic_telephone),
                    title = "Mobile",
                    subTitle = contactEntity.phoneNumber
                ),
                Item(
                    visualMedia = painterResource(R.drawable.ic_email),
                    title = "E-mail",
                    subTitle = contactEntity.email
                ),
                Item(
                    visualMedia = painterResource(R.drawable.ic_calendar),
                    title = "Birthday",
                    subTitle = contactEntity.birthday.toString()
                ),
                Item(
                    visualMedia = painterResource(R.drawable.ic_address),
                    title = "Address",
                    subTitle = contactEntity.address
                )
            )
            Column(
                modifier = Modifier
                    .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                userMetadataList.forEachIndexed { index, item ->

                    if (index > 0) {
                        HorizontalDivider(
                            modifier = Modifier
                                .background(color = LightGray)
                                .padding(vertical = 0.dp),
                            thickness = 1.dp,
                            color = LightGray
                        )
                    }
                    ListItem(
                        modifier = Modifier
                            .background(color = GrayOne)
                            .fillMaxWidth(),
                        item = item,
                        endContent = null,
                    )
                }
            }
        }
    }
}


data class Item(val visualMedia: Painter, val title: String, val subTitle: String)

@Composable
fun CustomButtonMedia(
    mainVisualMedia: Painter,
    alternativeVisualMedia: Painter? = null,
    badge: Painter? = null,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(8.dp),
    color: Color = GrayLightOne,
    onClick: (() -> Unit)? = null
) {
    var currentPainter by remember { mutableStateOf(mainVisualMedia) }

    Box {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = color, shape = CircleShape)
                .clickable {
                    currentPainter =
                        if (currentPainter == mainVisualMedia && alternativeVisualMedia != null) {
                            alternativeVisualMedia
                        } else {
                            mainVisualMedia
                        }
                    onClick?.invoke()
                }
                .defaultMinSize(56.dp, 56.dp),
        ) {

            // Main image
            Image(
                painter = currentPainter,
                contentDescription = null,
                modifier = modifier
                    .padding(padding)
            )
        }
        badge?.let {
            Image(
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(R.drawable.ic_star),
            )
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    item: Item,
    endContent: (@Composable (() -> Unit))? = null
) {

    Row(
        modifier = modifier
            .background(color = Color.Transparent)
            .clickable(onClick = { /* Item clicked */ }),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            modifier = Modifier
                .size(64.dp)
                .padding(20.dp),
            painter = item.visualMedia,
            tint = BlackTwo,
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(end = 32.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {

            Text(
                text = item.title,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.figtree_semibold)),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.subTitle,
                color = BlackTwo,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.figtree_regular)),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
        }
        endContent
    }
}

@Preview(showBackground = false, name = "Preview Contact Screen")
@Composable
fun PreviewContact() {
//    val viewModel: TelephoneViewModel by viewModels()
//    ContactScreen(onShareContact = {  }, onNavigationUp = {  })
}