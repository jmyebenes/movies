package dev.jmyp.movies.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.jmyp.movies.ui.components.LoadingOverlay
import dev.jmyp.movies.ui.theme.AppTheme

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    snackBarMessage: String? = null,
    onSnackBarDismissed: (() -> Unit)? = null,
    isLoading: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    AppTheme {
        val snackBarHostState = remember { SnackbarHostState() }

        LaunchedEffect(snackBarMessage) {
            snackBarMessage?.let {
                snackBarHostState.showSnackbar(it)
                onSnackBarDismissed?.invoke()
            }
        }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0),
            topBar = { topBar?.invoke() },
            bottomBar = { bottomBar?.invoke() },
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    )
                    .fillMaxSize()
            ) {
                content(innerPadding)

                if (isLoading) {
                    LoadingOverlay()
                }
            }
        }
    }
}