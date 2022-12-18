package org.deafsapps.shortlyapp.urlhistory.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deafsapps.shortlyapp.R
import org.deafsapps.shortlyapp.ui.theme.DarkViolet
import org.deafsapps.shortlyapp.ui.theme.Shapes
import org.deafsapps.shortlyapp.ui.theme.ShortlyAppTheme
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpResultBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOpStatusBo
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo

@Composable
fun ShortenedUrlHistoryScreen(
    list: List<ShortenUrlOperationBo>,
    onRemoveSelected: (ShortenUrlOperationBo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = colorResource(id = R.color.off_white))
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.your_link_history),
            color = colorResource(id = R.color.dark_violet),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(items = list, key = { item -> item.uuid }) { url ->
                UrlHistoryItem(
                    shortenUrl = url,
                    onRemoveSelected = onRemoveSelected
                )
            }
        }
    }
}

@Composable
private fun UrlHistoryItem(
    modifier: Modifier = Modifier,
    shortenUrl: ShortenUrlOperationBo,
    onRemoveSelected: (ShortenUrlOperationBo) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = Color.White, shape = Shapes.large)
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = shortenUrl.result.originalLink,
                    color = colorResource(id = R.color.dark_violet),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.weight(1F)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_trash), 
                    contentDescription = "trash",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onRemoveSelected(shortenUrl) }
                )
            }
            Divider(color = DarkViolet, thickness = 1.dp)
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = shortenUrl.result.fullShortLink,
                    color = colorResource(id = R.color.cyan),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.cyan)),
                    onClick = {
                    },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.copy),
                        color = colorResource(id = R.color.white),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ShortenedUrlHistoryScreenPreview() {
    ShortlyAppTheme {
        ShortenedUrlHistoryScreen(list = emptyList(), onRemoveSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun UrlHistoryItemPreview() {
    ShortlyAppTheme {
        UrlHistoryItem(shortenUrl = getDefaultShortenUrlOperationBo(), onRemoveSelected = {})
    }
}

private fun getDefaultShortenUrlOperationBo(): ShortenUrlOperationBo =
    ShortenUrlOperationBo(
        status = ShortenUrlOpStatusBo(isSuccessful = true),
        result = getDefaultShortenUrlOpResultBo()
    )

private fun getDefaultShortenUrlOpResultBo(): ShortenUrlOpResultBo =
    ShortenUrlOpResultBo(
        code = "KCveN",
        shortLink = "shrtco.de/KCveN",
        fullShortLink = "https://shrtco.de/KCveN",
        originalLink = "http://example.org/very/long/link.html"
    )
