package com.devmobile.android.luminatephoneapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.devmobile.android.luminatephoneapp.R
import com.devmobile.android.luminatephoneapp.data.CallEntity
import com.devmobile.android.luminatephoneapp.ui.components.CustomSearchBar
import com.devmobile.android.luminatephoneapp.ui.components.FilterChipList
import com.devmobile.android.luminatephoneapp.ui.components.MenuItem
import com.devmobile.android.luminatephoneapp.ui.components.SimpleMenu
import com.devmobile.android.luminatephoneapp.ui.theme.GrayLightThree
import com.devmobile.android.luminatephoneapp.ui.theme.GrayOne
import com.devmobile.android.luminatephoneapp.ui.theme.Typography
import com.devmobile.android.luminatephoneapp.utils.Filter
import com.devmobile.android.luminatephoneapp.viewmodel.TelephoneViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CallsScreen(modifier: Modifier = Modifier, viewModel: TelephoneViewModel = hiltViewModel()) {
    val callEntities = viewModel.calls.collectAsStateWithLifecycle(initialValue = emptyList())
    val filters = viewModel.filters.collectAsStateWithLifecycle()

    TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    CallScreenContent(
        calls = callEntities.value,
        filters = filters.value,
        onFilterChange = viewModel::onFilter,
        onSearch = viewModel::searchCalls,
        onSelect = { /* Nothing here */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallScreenContent(
    calls: List<CallEntity>,
    filters: List<Filter>,
    onFilterChange: (Filter) -> Unit,
    onSearch: (String) -> Unit,
    onSelect: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(horizontal = 0.dp, vertical = 0.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                TopAppBar(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                    title = {
                        Text(
                            text = stringResource(R.string.calls),
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
                            menusItems = listOf(MenuItem(name = stringResource(R.string.menu_options))),
                            onMenuClick = { Log.d("DEBUGGING", "Item clicked") }
                        )
                    },
                    scrollBehavior = scrollBehavior
                )

                CustomSearchBar(
                    onSearch = onSearch,
                    placeHolder = stringResource(R.string.search_holder),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )

                FilterChipList(
                    filters = filters,
                    onSelect = { newFilter ->

                        onFilterChange(newFilter)
                    },
                )
            }
        }
    ) { innerPadding ->

        if (calls.isEmpty()) {

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.on_empty_calls),
                    color = GrayLightThree,
                    modifier = Modifier,
                    fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
                    style = MaterialTheme.typography.displayLarge,
                )
            }

        } else {

            CallsList(
                modifier = Modifier.padding(innerPadding),
                calls = calls,
                onSelect = onSelect
            )
        }
    }
}

@Composable
fun CallsList(modifier: Modifier = Modifier, calls: List<CallEntity>, onSelect: (Int) -> Unit) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .background(color = Color.White)
            .padding(top = 8.dp),
        state = listState
    ) {

        items(calls) { item ->

            CallItem(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = {
                        onSelect(item.id)
                    }
                ), call = item)
        }
    }
}

@Composable
fun CallItem(call: CallEntity, modifier: Modifier = Modifier) {

//    Column(
//        modifier = modifier
//            .clickable(
//                enabled = true,
//                onClick = { /* It should show the call screen */ }
//            ),
//    ) {
    Row(
        modifier = modifier
            .clickable(
                enabled = true,
                onClick = { /* It should show the call screen */ }
            )
            .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier
                .background(color = GrayOne, shape = CircleShape)
                .size(48.dp)
                .fillMaxHeight(),
            contentPadding = PaddingValues(start = 0.dp),
            shape = CircleShape,
            colors = ButtonColors(
                containerColor = GrayOne,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified
            ),
            onClick = { Log.d("DEBUGGING", "image clicked") },
        ) {

//            if (call.contact.photo == null) {
            AsyncImage(
                model = R.drawable.ic_person,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
//            } else {
//                AsyncImage(
//                    model = call.contact.photo,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                )
//            }
//            if (call.contact.isFavorite) {
            // set a star
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Tiago",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = call.status,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
    Text(
        text = call.time,
        fontSize = 16.sp,
    )
}
//    }
