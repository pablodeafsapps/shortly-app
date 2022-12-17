package org.deafsapps.shortlyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.deafsapps.shortlyapp.common.base.StatefulViewModel
import org.deafsapps.shortlyapp.common.data.db.ApplicationDatabase
import org.deafsapps.shortlyapp.common.data.repository.UrlRepository
import org.deafsapps.shortlyapp.common.presentation.viewmodel.ShortenUrlViewModel
import org.deafsapps.shortlyapp.common.utils.getRetrofitInstance
import org.deafsapps.shortlyapp.ui.theme.ShortlyAppTheme
import org.deafsapps.shortlyapp.urlhistory.data.datasource.ShortenedUrlHistoryDataSource
import org.deafsapps.shortlyapp.urlhistory.domain.usecase.FetchAllShortenedUrlsAsyncUc
import org.deafsapps.shortlyapp.urlhistory.presentation.view.ShortenedUrlHistoryScreen
import org.deafsapps.shortlyapp.urlshortening.data.datasource.ShrtcodeDatasource
import org.deafsapps.shortlyapp.urlshortening.domain.model.ShortenUrlOperationBo
import org.deafsapps.shortlyapp.urlshortening.domain.usecase.ShortenAndPersistUrlUc
import org.deafsapps.shortlyapp.urlshortening.presentation.view.WelcomeScreen
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {

    private val shortenUrlViewModelProvider : ShortenUrlViewModel.Provider by lazy {
        ShortenUrlViewModel.Provider(
            shortenAndPersistUrlUc = ShortenAndPersistUrlUc(
                shortenUrlRepository = UrlRepository.apply {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    shortenUrlDatasource = ShrtcodeDatasource(
                        retrofit = getRetrofitInstance(converterFactory = MoshiConverterFactory.create(moshi))
                    )
                }, urlHistoryRepository = UrlRepository.apply {
                    urlHistoryDatasource = ShortenedUrlHistoryDataSource(
                        roomDatabaseInstance = Room.databaseBuilder(
                            applicationContext, ApplicationDatabase::class.java, "shorten-url-db"
                        ).build()
                    )
                }),
            fetchAllShortenedUrlsAsyncUc = FetchAllShortenedUrlsAsyncUc(
                urlHistoryRepository = UrlRepository.apply {
                    urlHistoryDatasource = ShortenedUrlHistoryDataSource(
                        roomDatabaseInstance = Room.databaseBuilder(
                            applicationContext, ApplicationDatabase::class.java, "shorten-url-db"
                        ).build()
                    )
                }),
            owner = this
        )
    }
    private val shortenUrlViewModel: StatefulViewModel<ShortenUrlViewModel.UiState> by lazy {
        ViewModelProvider(this, shortenUrlViewModelProvider)[ShortenUrlViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShortlyApp(viewModel = shortenUrlViewModel as ShortenUrlViewModel) }
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
                        if (navController.currentDestination?.route == Welcome.route) {
                            navController.navigate(ShortenedUrlHistory.route)
                        }
                        viewModel.onShortenUrlSelected(urlString = urlString)
                    }
                )
            }
        ) { innerPadding ->
            ShortlyNavHost(
                navController = navController,
                shortenUrlList = uiState.shortenedUrlHistory,
                onRemoveSelected = { shortenUrl ->
                                   viewModel.onRemoveShortenUrlSelected(shortenUrl = shortenUrl)


                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
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

@Composable
private fun ShortlyBottomComponent(
    hasInputError: Boolean?,
    onShortenUrlSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputValue by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
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
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxSize()
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.cyan)),
            onClick = {
                onShortenUrlSelected(inputValue)
                inputValue = ""
                      },
            modifier = Modifier
                .padding(top = 10.dp)
                .height(64.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.shorten_it),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ShortenUrlPlaceholder(hasInputError: Boolean?) {
    Text(
        text = stringResource(id = if (hasInputError == true) R.string.please_add_a_link_here else R.string.shorten_a_link_here),
        color = colorResource(id = if (hasInputError == true) R.color.red else R.color.light_gray),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun ShortlyBottomComponentPreview() {
    ShortlyAppTheme {
        ShortlyBottomComponent(hasInputError = false, onShortenUrlSelected = {})
    }
}
