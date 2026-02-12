package com.mshell.shellcinema.ui.features.genre_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.ui.ui.theme.ShellCinemaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenreListScreen(
    viewModel: GenreViewModel = koinViewModel(),
    onItemClick: (Genre) -> Unit = {}
) {
    val genreState by viewModel.genreState.collectAsState()

    when(genreState) {
        is Resource.Success -> {
            val genreList = genreState.data
            if (genreList.isNullOrEmpty()) {
                return
            }

            GenreList(genreList, onItemClick = onItemClick)
        }
        else -> {}
    }
}

@Composable
fun GenreList(
    genreList: List<Genre>,
    onItemClick: (Genre) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = genreList,
            key = { it.id ?: 0 },
        ) { genre ->
            GenreItemCard(
                genre,
                onClick = { onItemClick(genre) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun GenreListScreenPreviewDark() {
    val sampleGenres = listOf(
        Genre(
            id = 1,
            name = "Action"
        ),
        Genre(
            id = 2,
            name = "Comedy"
        ),
        Genre(
            id = 3,
            name = "Drama"
        )
    )

    ShellCinemaTheme {
        GenreList(genreList = sampleGenres)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun GenreListScreenPreviewLight() {
    val sampleGenres = listOf(
        Genre(
            id = 1,
            name = "Action"
        ),
        Genre(
            id = 2,
            name = "Comedy"
        ),
        Genre(
            id = 3,
            name = "Drama"
        )
    )

    ShellCinemaTheme {
        GenreList(genreList = sampleGenres)
    }
}



