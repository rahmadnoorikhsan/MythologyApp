package com.ikhsan.compose.mythology.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ikhsan.compose.mythology.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onActiveChange(false)
        },
        active = false,
        onActiveChange = onActiveChange,
        placeholder = {
            Text(stringResource(R.string.search))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    if (query.isNotEmpty()) {
                        onQueryChange("")
                    } else {
                        onActiveChange(false)
                    }
                },
                tint = if (query.isNotEmpty()) {
                    Color.DarkGray
                } else {
                    Color.Transparent
                },
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_button),
            )
        }
    ) {
    }
}