package com.devmobile.android.luminatephoneapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.devmobile.android.luminatephoneapp.R
import com.devmobile.android.luminatephoneapp.data.ContactEntity
import com.devmobile.android.luminatephoneapp.ui.components.CustomSearchBar
import com.devmobile.android.luminatephoneapp.ui.components.FilterChipList
import com.devmobile.android.luminatephoneapp.ui.components.MenuItem
import com.devmobile.android.luminatephoneapp.ui.components.SimpleMenu
import com.devmobile.android.luminatephoneapp.ui.theme.BlackTwo
import com.devmobile.android.luminatephoneapp.ui.theme.GrayLightThree
import com.devmobile.android.luminatephoneapp.ui.theme.Typography
import com.devmobile.android.luminatephoneapp.utils.Filter
import com.devmobile.android.luminatephoneapp.viewmodel.TelephoneViewModel

@Composable
fun ContactsScreen(viewModel: TelephoneViewModel, goToRoute: (Int) -> Unit) {
    val filters = viewModel.filters.collectAsStateWithLifecycle()
    val contacts = viewModel.contacts.collectAsStateWithLifecycle(emptyList())

    ContactsScreenContent(
        contacts = contacts.value,
        filters = filters.value,
        onFilterChange = viewModel::onFilter,
        onSearch = { query -> viewModel.searchCalls(query) },
        onNavigate = goToRoute
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreenContent(
    contacts: List<ContactEntity>,
    filters: List<Filter>,
    onFilterChange: (Filter) -> Unit,
    onSearch: (String) -> Unit,
    onNavigate: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .background(color = Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(horizontal = 0.dp, vertical = 0.dp)
                    .background(color = Color.Unspecified)
            ) {
                TopAppBar(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 0.dp
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.contacts),
                            style = Typography.titleLarge,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    actions = {
                        SimpleMenu(
                            modifier = Modifier.padding(8.dp),
                            menuIcon = painterResource(R.drawable.ic_options),
                            menusItems = listOf(MenuItem(name = "Menu 1")),
                            onMenuClick = {
                                Log.d("DEBUGGING", "Clicked")
                            }
                        )
                    },
                    scrollBehavior = scrollBehavior
                )

                CustomSearchBar(
                    onSearch = onSearch,
                    placeHolder = "Search for contacts",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )

                FilterChipList(
                    filters = filters,
                    onSelect = { filterSelected ->
                        onFilterChange(filterSelected)
                    },
                )
            }
        }
    ) { innerPadding ->

        if (contacts.isEmpty()) {

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.on_empty_contacts),
                    color = GrayLightThree,
                    modifier = Modifier,
                    fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
                    style = MaterialTheme.typography.displayLarge,
                )
            }

        } else {

            ContactList(
                modifier = Modifier.padding(innerPadding),
                contactEntities = contacts,
                onSelectContact = onNavigate,
            )
        }
    }
}

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contactEntities: List<ContactEntity>,
    onSelectContact: (Int) -> Unit
) {

    val listState = rememberLazyListState()

    LazyColumn(
        modifier
            .background(color = Color.White)
            .padding(top = 8.dp),
        state = listState
    ) {
        items(
            items = contactEntities,
            key = { it.id }
        ) { contact ->

            ContactItem(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = {
                        onSelectContact(contact.id)
                    }
                ),
                contactEntity = contact,
            )
        }
    }
}

@Composable
fun ContactItem(modifier: Modifier = Modifier, contactEntity: ContactEntity) {

    Row(
        modifier = modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        CustomButtonMedia(
            modifier = Modifier
                .size(56.dp)
                .background(shape = CircleShape, color = Color.Transparent),
            badge = if (contactEntity.isFavorite) rememberAsyncImagePainter(R.drawable.ic_star) else null,
            mainVisualMedia = rememberAsyncImagePainter(
                contactEntity.photo ?: R.drawable.ic_person
            ),
            padding = PaddingValues(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = contactEntity.name,
                style = Typography.titleMedium,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = contactEntity.phoneNumber,
                fontSize = 16.sp,
                color = BlackTwo,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}