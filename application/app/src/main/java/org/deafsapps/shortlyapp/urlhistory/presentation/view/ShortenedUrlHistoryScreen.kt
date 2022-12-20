package org.deafsapps.shortlyapp.urlhistory.presentation.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
            modifier = modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
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
        LaunchedEffect(key1 = listState, block = {
            listState.animateScrollToItem(list.size)
        })
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
                modifier = modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = shortenUrl.result.originalLink,
                    color = colorResource(id = R.color.dark_violet),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = modifier.weight(1F)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_trash), 
                    contentDescription = "trash",
                    modifier = modifier
                        .padding(4.dp)
                        .clickable { onRemoveSelected(shortenUrl) }
                )
            }
            Divider(color = DarkViolet, thickness = 1.dp)
            Column(
                modifier = modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
            ) {
                val context: Context = LocalContext.current
                Text(
                    text = shortenUrl.result.fullShortLink,
                    color = colorResource(id = R.color.cyan),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = modifier.fillMaxWidth()
                )

                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = if (isPressed) R.color.cyan else R.color.dark_violet)),
                    onClick = {
                        context.copyShortenUrlToClipboard(url = shortenUrl.result.shortLink)
                    },
                    interactionSource = interactionSource,
                    modifier = modifier
                        .padding(top = 24.dp)
                        .clip(shape = Shapes.large)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = if (isPressed) R.string.copied else R.string.copy),
                        color = colorResource(id = R.color.white),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

private fun Context.copyShortenUrlToClipboard(url: String) {
    val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("shorten-url", url)
    clipboardManager.setPrimaryClip(clip)
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
