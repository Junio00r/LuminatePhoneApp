package com.devmobile.android.luminatephoneapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devmobile.android.luminatephoneapp.ui.theme.BlackTwo
import com.devmobile.android.luminatephoneapp.ui.theme.GrayThree
import com.devmobile.android.luminatephoneapp.ui.theme.Typography
import com.devmobile.android.luminatephoneapp.utils.Filter

@Composable
fun FilterChipList(
    modifier: Modifier = Modifier,
    filters: List<Filter>, // I think hasn't necessity for a custom class here, it's just a telephone app :)
    onSelect: (Filter) -> Unit
) {
    val state = rememberLazyListState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = state,
    ) {

        itemsIndexed(
            items = filters,
            key = { index, filter -> filter.filterName }
        ) { index, filter ->

            val paddingValues = when (index) {
                0 -> {
                    PaddingValues(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 0.dp)
                }

                filters.lastIndex -> {
                    PaddingValues(top = 4.dp, bottom = 4.dp, start = 0.dp, end = 16.dp)
                }

                else -> {
                    PaddingValues(vertical = 4.dp, horizontal = 0.dp)
                }
            }

            FilterChip(
                modifier = Modifier.padding(paddingValues),
                shape = CircleShape,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = GrayThree,
                    disabledContainerColor = GrayThree,
                    selectedContainerColor = BlackTwo,
                    selectedLabelColor = Color.White,
                ),
                border = null,
                onClick = {
                    onSelect(filter)
                },
                selected = filter.isSelected,
                label = {
                    Text(
                        text = filter.filterName,
                        fontWeight = FontWeight.Normal,
                        style = Typography.labelMedium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                },
            )
        }
    }
}
