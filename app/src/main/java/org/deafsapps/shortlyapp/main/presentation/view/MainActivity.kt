package org.deafsapps.shortlyapp.main.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.deafsapps.shortlyapp.R
import org.deafsapps.shortlyapp.ShortlyApplication
import org.deafsapps.shortlyapp.common.presentation.viewmodel.ShortenUrlViewModel
import org.deafsapps.shortlyapp.main.di.MainComponent
import org.deafsapps.shortlyapp.main.di.MainPresentationModule
import org.deafsapps.shortlyapp.main.navigation.ShortenedUrlHistory
import org.deafsapps.shortlyapp.main.navigation.Welcome
import org.deafsapps.shortlyapp.ui.theme.Shapes
import org.deafsapps.shortlyapp.ui.theme.ShortlyAppTheme
import org.deafsapps.shortlyapp.urlhistory.presentation.view.ShortenedUrlHistoryScreen
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.presentation.view.WelcomeScreen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var shortenUrlViewModelProvider: ShortenUrlViewModel.Provider
    private val shortenUrlViewModel: ShortenUrlViewModel by lazy {
        ViewModelProvider(this, shortenUrlViewModelProvider)[ShortenUrlViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getApplicationComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContent { ShortlyApp(viewModel = shortenUrlViewModel) }
    }

}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
private fun ShortlyApp(viewModel: ShortenUrlViewModel) {
    ShortlyAppTheme {
        val navController: NavHostController = rememberNavController()
        val uiState: ShortenUrlViewModel.UiState by viewModel.uiState.collectAsStateWithLifecycle()
        Scaffold(
            bottomBar = {
                ShortlyBottomComponent(
                    hasInputError = uiState.hasInputError,
                    onShortenUrlSelected = { urlString ->
                        viewModel.onShortenUrlSelected(urlString = urlString)
                    }
                )
            }
        ) { innerPadding ->
            navController.navigateToUrlHistoryIfPredicate(
                predicate = booleanArrayOf(
                    uiState.shortenedUrlHistory.isNotEmpty(),
                    uiState.hasInputError == false
                )
            )
            ShortlyNavHost(
                navController = navController,
                shortenUrlList = uiState.shortenedUrlHistory,
                onRemoveSelected = { shortenUrl ->
                    viewModel.onRemoveShortenUrlSelected(url = shortenUrl)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

private fun MainActivity.getApplicationComponent(): MainComponent =
    (application as ShortlyApplication).provideMainComponentFactory()
        .create(presentationModule = MainPresentationModule(this))

private fun NavController.navigateToUrlHistoryIfPredicate(vararg predicate: Boolean) {
    if (predicate.all { it }) { navigateSingleTop(ShortenedUrlHistory.route) }
}

private fun NavController.navigateSingleTop(route: String) {
    navigate(route) { launchSingleTop = true }
}

@Composable
private fun ShortlyNavHost(
    navController: NavHostController,
    shortenUrlList: List<ShortenUrlOperationBo>,
    onRemoveSelected: (ShortenUrlOperationBo) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Welcome.route,
        modifier = modifier
    ) {
        composable(route = Welcome.route) {
            WelcomeScreen()
        }
        composable(route = ShortenedUrlHistory.route) {
            ShortenedUrlHistoryScreen(list = shortenUrlList, onRemoveSelected = onRemoveSelected)
        }
    }

}

const val SHORTLY_BOTTOM_COMPONENT_URL_TEXT_FIELD_TAG = "shortlyBottomComponentUrlTextFieldTag"

@Composable
fun ShortlyBottomComponent(
    hasInputError: Boolean?,
    onShortenUrlSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputValue by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.dark_violet))
            .paint(
                painter = painterResource(R.drawable.default_shape), alignment = Alignment.TopEnd
            )
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                isError = hasInputError ?: false,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.white)
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
                ),
                placeholder = { ShortenUrlPlaceholder(hasInputError = hasInputError) },
                modifier = modifier
                    .clip(shape = Shapes.large)
                    .height(50.dp)
                    .fillMaxWidth()
                    .testTag(SHORTLY_BOTTOM_COMPONENT_URL_TEXT_FIELD_TAG)
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.cyan)),
            onClick = {
                onShortenUrlSelected(inputValue)
                inputValue = ""
                      },
            modifier = modifier
                .padding(top = 10.dp)
                .clip(shape = Shapes.large)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.shorten_it),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ShortenUrlPlaceholder(
    hasInputError: Boolean?,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = if (hasInputError == true) R.string.please_add_a_link_here else R.string.shorten_a_link_here),
        color = colorResource(id = if (hasInputError == true) R.color.red else R.color.light_gray),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
private fun ShortlyBottomComponentPreview() {
    ShortlyAppTheme {
        ShortlyBottomComponent(hasInputError = false, onShortenUrlSelected = {})
    }
}
