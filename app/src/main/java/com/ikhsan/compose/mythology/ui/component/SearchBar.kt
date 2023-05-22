package com.ikhsan.compose.mythology.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ikhsan.compose.mythology.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String, Boolean) -> Unit,
    active: Boolean,
    history: List<String>,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onActiveChange(false)
            onSearch(it, true)
        },
        active = active,
        onActiveChange = onActiveChange,
        placeholder = {
            Text(stringResource(R.string.search))
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    onActiveChange(false)
                    onSearch(query, true)
                },
                modifier = Modifier.testTag("search_icon")
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                )
            }
        },
        trailingIcon = {
            if (active) {
                IconButton(onClick = {
                    if (query.isNotEmpty()) {
                        onQueryChange("")
                    } else {
                        onActiveChange(false)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_button),
                    )
                }
            }
        }
    ) {
        history.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onActiveChange(false)
                        onSearch(it, false)
                    }
            ) {
                Icon(
                    modifier = Modifier.padding(16.dp),
                    imageVector = Icons.Default.History,
                    contentDescription = stringResource(R.string.history_icon)
                )
                Text(text = it, modifier = Modifier.padding(vertical = 16.dp))
            }
        }
    }
}