package dev.jmyp.movies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.jmyp.movies.R
import dev.jmyp.movies.data.model.Cast
import dev.jmyp.movies.data.model.Crew
import dev.jmyp.movies.ui.navigation.PersonCategory

@Composable
fun CreditsView(
    cast: List<Cast>?,
    crew: List<Crew>?,
    onPersonClick: (Int) -> Unit,
    onSeeAllCastClick: ((PersonCategory) -> Unit)? = null,
    onSeeAllCrewClick: ((PersonCategory) -> Unit)? = null,
) {
    TitleView(
        title = stringResource(R.string.movie_detail_title_cast),
        onClick = onSeeAllCastClick?.let { callback -> { callback(PersonCategory.CAST) } })
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(cast?.size ?: 0) { index ->
            val cast = cast?.get(index) ?: return@items
            PersonView(
                PersonItem(
                    id = cast.id,
                    title = cast.name,
                    subtitle = cast.character,
                    image = cast.profilePath
                ),
                modifier = Modifier.width(88.dp)
            ) {
                onPersonClick(cast.id)
            }
        }
    }

    TitleView(
        title = stringResource(R.string.movie_detail_title_crew),
        onClick = onSeeAllCrewClick?.let { callback -> { callback(PersonCategory.CREW) } })
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(crew?.size ?: 0) { index ->
            val crew = crew?.get(index) ?: return@items
            PersonView(
                PersonItem(
                    id = crew.id,
                    title = crew.name,
                    subtitle = crew.job,
                    image = crew.profilePath
                ),
                modifier = Modifier.width(88.dp)
            ) {
                onPersonClick(crew.id)
            }
        }
    }
}