package dev.jmyp.movies.ui

import androidx.compose.runtime.Composable
import dev.jmyp.movies.ui.navigation.AppNavHost
import dev.jmyp.movies.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        AppNavHost()
    }
}