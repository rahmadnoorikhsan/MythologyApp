package com.ikhsan.compose.mythology.ui.screen.favorite

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikhsan.compose.mythology.R
import com.ikhsan.compose.mythology.model.Mythology
import com.ikhsan.compose.mythology.ui.common.UiState
import com.ikhsan.compose.mythology.ui.component.EmptyContent
import com.ikhsan.compose.mythology.ui.screen.home.ListMythology
import com.ikhsan.compose.mythology.ui.theme.MythologyTheme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Error -> {}
            is UiState.Loading -> {
                viewModel.getFavoriteMythology()
            }
            is UiState.Success -> {
                FavoriteContent(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    mythologyItem: List<Mythology>,
    onFavClick: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.menu_favorite))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        modifier = modifier
    ) {
        if (mythologyItem.isNotEmpty()) {
            ListMythology(
                mythologyItem = mythologyItem,
                onFavClick = onFavClick,
                contentPadding = it,
                navigateToDetail = navigateToDetail,
                modifier = Modifier
                    .padding(16.dp)
            )
        } else {
            EmptyContent(
                image = R.drawable.emptydata,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    MythologyTheme {
        FavoriteScreen(navigateToDetail = {})
    }
}