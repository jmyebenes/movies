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
fun BiographyView(biography: String?) {
    if (biography.isNullOrEmpty()) return
    Text(
        text = stringResource(R.string.person_detail_title_biography),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = biography,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}