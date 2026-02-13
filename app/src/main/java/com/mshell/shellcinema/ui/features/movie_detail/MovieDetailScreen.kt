package com.mshell.shellcinema.ui.features.movie_detail
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.MovieDetail
import com.mshell.shellcinema.ui.features.movie_detail.components.ReviewBottomSheet
import com.mshell.shellcinema.ui.features.movie_detail.components.VideoBottomSheet
import org.koin.androidx.compose.koinViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onPlayVideo: (String) -> Unit = {}
) {
    val movieState by viewModel.movieState.collectAsState()
    val reviews = viewModel.reviewsState.collectAsLazyPagingItems()
    val videosState by viewModel.videosState.collectAsState()
    var showReviewBottomSheet by remember { mutableStateOf(false) }
    var showVideoBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (movieState) {
                        is Resource.Success -> Text((movieState as Resource.Success).data?.title ?: "Movie Details")
                        else -> Text("Movie Details")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (movieState) {
                is Resource.Loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator()
                        Text("Loading movie details...")
                    }
                }
                is Resource.Error -> {
                    val message = (movieState as Resource.Error).errorMessage ?: "Unknown error"
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.getMovieDetails(movieId) }) {
                            Text("Retry")
                        }
                    }
                }
                is Resource.Success -> {
                    val movieDetail = (movieState as Resource.Success).data
                    if (movieDetail != null) {
                        MovieDetailContent(
                            movieDetail = movieDetail,
                            onShowReviews = {
                                viewModel.loadReviews()
                                showReviewBottomSheet = true
                            },
                            onShowVideos = {
                                viewModel.loadVideos()
                                showVideoBottomSheet = true
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("No movie data available")
                    }
                }
            }
        }
        if (showReviewBottomSheet) {
            ReviewBottomSheet(
                reviews = reviews,
                onDismiss = { showReviewBottomSheet = false },
            )
        }
        if (showVideoBottomSheet) {
            VideoBottomSheet(
                videosState = videosState,
                onDismiss = { showVideoBottomSheet = false },
                onPlayVideo = onPlayVideo
            )
        }
    }
}
@Composable
fun MovieDetailContent(
    movieDetail: MovieDetail,
    onShowReviews: () -> Unit = {},
    onShowVideos: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movieDetail.posterPath}",
                contentDescription = movieDetail.title,
                modifier = Modifier
                    .width(230.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "%.1f".format(
                            when (val voteAvg = movieDetail.voteAverage) {
                                is Number -> voteAvg.toDouble()
                                else -> 0.0
                            }
                        ),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                movieDetail.releaseDate?.let { date ->
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = movieDetail.overview ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                textAlign = TextAlign.Justify
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onShowReviews,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "User Reviews",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Button(
                onClick = onShowVideos,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "Watch Trailers",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
