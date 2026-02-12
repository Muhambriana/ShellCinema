package com.mshell.shellcinema.ui.features.movie_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.itemKey
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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = movies.itemCount,
            key = movies.itemKey { it.id!! }
        ) { index ->
            movies[index]?.let { movie ->
                MovieItemCard(
                    movie = movie,
                    onClick = { onItemClick(movie) }
                )
            }
        }

        // Handle pagination loading
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
        // Note: Previews don't work well with PagingData
        // Consider using a fake ViewModel or mock data
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Preview not available for PagingData")
        }
    }
}
