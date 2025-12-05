package dev.jmyp.movies.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.jmyp.movies.R

@Composable
fun SynopsisView(overview: String?) {
    if (overview.isNullOrEmpty()) return
    Text(
        stringResource(R.string.movie_detail_title_synopsis),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = overview,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}