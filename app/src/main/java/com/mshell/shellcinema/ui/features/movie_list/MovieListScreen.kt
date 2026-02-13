package com.mshell.shellcinema.ui.features.movie_list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mshell.shellcinema.core.domain.model.Movie
import com.mshell.shellcinema.ui.ui.theme.ShellCinemaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onItemClick: (Movie) -> Unit = {}
) {
    val moviesState = viewModel.movies.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onBackClick() }
        ) {
            Text(text = viewModel.genreName)
        }

        when (moviesState.loadState.refresh) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                val error = (moviesState.loadState.refresh as LoadState.Error).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${error.localizedMessage}")
                }
            }
            is LoadState.NotLoading -> {
                if (moviesState.itemCount == 0) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No movies found")
                    }
                } else {
                    MovieList(movies = moviesState, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Movie) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = movies.itemCount
        ) { index ->
            val movie = movies[index]
            if (movie != null) {
                MovieItemCard(
                    movie = movie,
                    onClick = { onItemClick(movie) }
                )
            }
        }

        when (movies.loadState.append) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is LoadState.Error -> {
                val error = (movies.loadState.append as LoadState.Error).error
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${error.localizedMessage}")
                    }
                }
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieListScreenPreviewDark() {
    ShellCinemaTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Preview not available for PagingData")
        }
    }
}
