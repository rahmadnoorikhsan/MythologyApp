package com.ikhsan.compose.mythology.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikhsan.compose.mythology.R
import com.ikhsan.compose.mythology.model.Mythology
import com.ikhsan.compose.mythology.ui.common.UiState
import com.ikhsan.compose.mythology.ui.component.CardItem
import com.ikhsan.compose.mythology.ui.component.EmptyContent
import com.ikhsan.compose.mythology.ui.component.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Error -> {}
            is UiState.Loading -> {
                viewModel.getMythology()
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    onActiveChange = viewModel::active,
                    mythologyItem = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavClick = { id, newState ->
                        viewModel.updateMythology(id, newState)
                    },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun HomeContent (
    query: String,
    onQueryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    mythologyItem: List<Mythology>,
    onFavClick: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    Column(modifier = modifier) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onActiveChange = onActiveChange
        )
        if (mythologyItem.isNotEmpty()) {
            ListMythology(
                mythologyItem = mythologyItem,
                onFavClick = onFavClick,
                navigateToDetail = navigateToDetail,
                contentPadding = PaddingValues(16.dp)
            )
        } else {
            EmptyContent(
                image = R.drawable.emptydata
            )
        }
    }
}

@Composable
fun ListMythology(
    mythologyItem: List<Mythology>,
    onFavClick: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding,
        modifier = modifier
        ) {
        items(mythologyItem, key = { it.id }) { data ->
            CardItem(
                id = data.id,
                urlImage = data.image,
                title = data.title,
                isFav = data.favorite,
                onFavClick = onFavClick,
                modifier = Modifier
                    .clickable {
                        navigateToDetail(data.id)
                    }
            )
        }
    }
}

