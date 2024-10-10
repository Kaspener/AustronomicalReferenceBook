package com.example.astronomicalreferencebook

import android.content.Context
import androidx.compose.ui.Alignment
import android.opengl.GLSurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

class SquvareGL {

    @Composable
    fun MainView() {
        var currentScreen by remember { mutableStateOf(Screen.OpenGL) }

        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                Screen.OpenGL -> {
                    OpenGLView(currentScreen = currentScreen, onScreenChange = { screen ->
                        currentScreen = screen
                    })
                }
                Screen.Advertisement -> {
                    AdvertisementWindow(currentScreen = currentScreen, onScreenChange = { screen ->
                        currentScreen = screen
                    })
                }
                Screen.Moon -> {
                    MoonInfoScreen(onBack = {
                        currentScreen = Screen.OpenGL
                    })
                }
            }
        }
    }

    @Composable
    fun OpenGLView(currentScreen: Screen, onScreenChange: (Screen) -> Unit) {
        val context = LocalContext.current
        val renderer = remember { GL(context) }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = {
                GLSurfaceView(context).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(renderer)
                    renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                }
            }, modifier = Modifier.fillMaxSize())

            Button(
                onClick = {
                    onScreenChange(if (currentScreen == Screen.OpenGL) Screen.Advertisement else Screen.OpenGL)
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                Text("Переключить экран")
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        var temp = renderer.selectedPlanet - 1
                        if (temp < 0) temp = renderer.planetsSize() - 1
                        renderer.selectedPlanet = temp % renderer.planetsSize()
                    },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text("◀")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { when (renderer.selectedPlanet) {
                        4 -> {onScreenChange(Screen.Moon)}
                        else -> {}
                    }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Информация")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { renderer.selectedPlanet = (renderer.selectedPlanet + 1) % renderer.planetsSize() },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text("▶")
                }
            }
        }
    }

    @Composable
    fun MoonInfoScreen(onBack: () -> Unit) {
        val context = LocalContext.current
        val moonRenderer = remember { MoonRenderer(context) }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = {
                GLSurfaceView(context).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(moonRenderer)
                    renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                }
            }, modifier = Modifier.fillMaxSize())

            Button(
                onClick = { onBack() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text("Назад")
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color(0x80000000)) // Полупрозрачный фон для лучшей читабельности
                    .padding(16.dp)
            ) {
                Text(
                    text = "Информация о Луне",
                    style = TextStyle(
                        fontSize = 20.sp, // Размер шрифта
                        fontWeight = FontWeight.Bold, // Полужирный шрифт
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Луна — единственный естественный спутник Земли и пятый по величине спутник в Солнечной системе.",
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Она находится на среднем расстоянии 384 400 км от Земли и имеет диаметр примерно 3 474 км.",
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Луна воздействует на приливы и отливы в океанах и является объектом многих исследований.",
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Её поверхность покрыта кратерами, луными морями и высокогорьями.",
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Луна не имеет собственной атмосферы, но её гравитация удерживает некоторые газы близко к поверхности.",
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun AdvertisementWindow(viewModel: AdvertisementViewModel = viewModel(), currentScreen: Screen, onScreenChange: (Screen) -> Unit) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(5000)
                viewModel.updateRandomNews()
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
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
            Button(
                onClick = {
                    onScreenChange(if (currentScreen == Screen.OpenGL) Screen.Advertisement else Screen.OpenGL)
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Text("Переключить экран")
            }
        }
    }

    enum class Screen {
        OpenGL,
        Advertisement,
        Moon
    }
}