package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import currencykmpapp.composeapp.generated.resources.Res
import currencykmpapp.composeapp.generated.resources.refresh
import org.example.project.peresentation.composeScreen.CurrencyListShow
import org.example.project.peresentation.composeScreen.HomeScreen
import org.example.project.peresentation.composeScreen.Screen
import org.example.project.peresentation.composeScreen.SearchScreen
import org.example.project.peresentation.viewModel.MainViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
@Composable
@Preview
fun App() {

    var currentScreen by mutableStateOf<Screen>(Screen.Home)


    MaterialTheme {

        when (currentScreen) {
                is Screen.Home -> HomeScreen(
                    onSearchClicked = {currentScreen = Screen.Search }
                )
                is Screen.Search -> SearchScreen(
                    onBack = { currentScreen = Screen.Home }
                )
            }
        }

}
