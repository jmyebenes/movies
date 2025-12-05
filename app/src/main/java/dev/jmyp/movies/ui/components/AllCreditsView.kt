package dev.jmyp.movies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AllCreditsView(allCredits: List<PersonItem>?, onClick: (Int) -> Unit) {
    if (allCredits.isNullOrEmpty()) return

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 104.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(allCredits.size) { index ->
            PersonView(
                PersonItem(
                    id = allCredits[index].id,
                    title = allCredits[index].title,
                    subtitle = allCredits[index].subtitle,
                    image = allCredits[index].image
                ),
            ) {
                onClick(allCredits[index].id)
            }
        }
    }
}