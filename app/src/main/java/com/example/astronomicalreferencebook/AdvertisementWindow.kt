package com.example.astronomicalreferencebook

import android.content.Context
import androidx.compose.ui.Alignment
import android.opengl.GLSurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astronomicalreferencebook.SquvareGL.Screen
import kotlinx.coroutines.delay

class SquvareGL {

    @Composable
    fun MainView() {
        var currentScreen by remember { mutableStateOf(Screen.OpenGL) }

        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                Screen.OpenGL -> {
                    OpenGLView(LocalContext.current)
                }
                Screen.Advertisement -> {
                    AdvertisementWindow()
                }
            }

            Button(
                onClick = {
                    currentScreen = if (currentScreen == Screen.OpenGL) Screen.Advertisement else Screen.OpenGL
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                Text("Переключить экран")
            }
        }
    }

    @Composable
    fun OpenGLView(context: Context) {
        Box(modifier = Modifier.fillMaxSize()) { // Используем Box для наложения элементов
            AndroidView(factory = {
                GLSurfaceView(context).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(GL(context))
                    renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                }
            })

        }
    }

    @Composable
    fun AdvertisementWindow(viewModel: AdvertisementViewModel = viewModel()) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(5000)
                viewModel.updateRandomNews()
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(Modifier.weight(1f)) {
                Advertisement(
                    modifier = Modifier.weight(1f),
                    advertisement = viewModel.displayedAdvertisements[0],
                    onLike = { viewModel.likeAdvertisement(it) }
                )
                Advertisement(
                    modifier = Modifier.weight(1f),
                    advertisement = viewModel.displayedAdvertisements[1],
                    onLike = { viewModel.likeAdvertisement(it) }
                )
            }
            Row(Modifier.weight(1f)) {
                Advertisement(
                    modifier = Modifier.weight(1f),
                    advertisement = viewModel.displayedAdvertisements[2],
                    onLike = { viewModel.likeAdvertisement(it) }
                )
                Advertisement(
                    modifier = Modifier.weight(1f),
                    advertisement = viewModel.displayedAdvertisements[3],
                    onLike = { viewModel.likeAdvertisement(it) }
                )
            }
        }
    }

    enum class Screen {
        OpenGL,
        Advertisement
    }
}
