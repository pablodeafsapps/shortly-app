package org.deafsapps.shortlyapp.urlshortening.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deafsapps.shortlyapp.R
import org.deafsapps.shortlyapp.ui.theme.ShortlyAppTheme

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "title",
            modifier = Modifier
                .size(88.dp)
                .weight(.4f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_illustration),
            contentDescription = "app logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.weight(1f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(.4f).padding(bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.get_started),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.paste_your_first_link),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ShortlyAppTheme {
        WelcomeScreen()
    }
}
