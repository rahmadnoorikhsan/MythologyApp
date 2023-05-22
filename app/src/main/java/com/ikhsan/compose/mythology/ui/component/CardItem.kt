package com.ikhsan.compose.mythology.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ikhsan.compose.mythology.R
import com.ikhsan.compose.mythology.ui.theme.MythologyTheme

@Composable
fun CardItem(
    id: Int,
    urlImage: String,
    title: String,
    isFav: Boolean,
    ignoredOnFavClick: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = urlImage,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp),
                contentScale = ContentScale.Crop
                )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
                IconButton(
                    onClick = { ignoredOnFavClick(id, !isFav) },
                    modifier = Modifier.testTag("fav_button"),
                    content = {
                        Icon(
                            imageVector = if (isFav) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.fav_button),
                            tint = if (!isFav) Color.DarkGray else Color.Red,
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    MythologyTheme {
        CardItem(
            id = 1,
            urlImage = "",
            title = "Mermaid",
            isFav = true,
            ignoredOnFavClick = { _, _ ->}
        )
    }
}