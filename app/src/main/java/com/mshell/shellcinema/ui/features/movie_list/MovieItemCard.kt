package com.mshell.shellcinema.ui.features.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mshell.shellcinema.core.domain.model.Movie
import com.mshell.shellcinema.core.utils.NetworkInfo
import com.mshell.shellcinema.ui.ui.theme.ShellCinemaTheme
import com.mshell.shellcinema.utils.Helper

@Composable
fun MovieItemCard(
    movie: Movie,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .wrapContentHeight()
            .padding(7.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(265.dp)
        ) {
            AsyncImage(
                model = "${NetworkInfo.BASE_URL_IMAGE}${movie.posterPath}",
                placeholder = Helper.getImagePlaceHolder(),
                error = Helper.getErrorPlaceHolder(),
                contentDescription = movie.title ?: "Movie poster",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(4.dp)
            ) {
                Text(
                    text = movie.title ?: "Unknown",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFFD700)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "%.1f".format(movie.voteAverage ?: 0.0),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Movie Card",
    showBackground = true
)
@Composable
fun MovieItemCardPreview() {
    ShellCinemaTheme {
        Box(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(16.dp)
        ) {
            MovieItemCard(
                movie = Movie(
                    id = 278,
                    title = "The Shawshank Redemption",
                    overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                    releaseDate = "1994-09-23",
                    genreIds = listOf(18, 80),
                    posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                    voteAverage = 8.7
                ),
            )
        }
    }
}

