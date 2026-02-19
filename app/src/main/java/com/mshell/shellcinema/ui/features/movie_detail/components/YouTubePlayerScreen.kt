package com.mshell.shellcinema.ui.features.movie_detail.components

import android.content.pm.ActivityInfo
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.mshell.shellcinema.utils.Helper.findActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerScreen(
    videoKey: String,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = context.findActivity()

    // 1. Manage System UI (Immersive Mode)
    // This hides the status bar and nav bar when this screen is active
    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        val window = activity?.window
        if (window != null) {
            val controller = WindowCompat.getInsetsController(window, window.decorView)
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            // Restore System UI when leaving this screen
            val window = activity?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, window.decorView).show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    val playerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            lifecycleOwner.lifecycle.addObserver(this)

            // IFramePlayerOptions usually takes no arguments or a lambda in the latest version
            val options = IFramePlayerOptions.Builder(context)
                .controls(1)
                .fullscreen(0)
                .build()

            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // Use loadVideo instead of cueVideo to start immediately for a "Screen"
                    youTubePlayer.loadVideo(videoKey, 0f)
                }
            }, options)

//            addFullscreenListener(object : FullscreenListener {
//                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
//                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
//                }
//                override fun onExitFullscreen() {
//                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                }
//            })
        }
    }

    // 2. The Container
    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Black background looks better for video screens
    )
}