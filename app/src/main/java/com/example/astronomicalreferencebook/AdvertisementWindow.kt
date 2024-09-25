package com.example.astronomicalreferencebook

import android.content.Context
import android.opengl.GLSurfaceView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

class SquvareGL
{
    @Composable
    fun OpenGLView(context: Context) {
        AndroidView(factory = {
            GLSurfaceView(context).apply {
                setEGLContextClientVersion(2)
                setRenderer(GL(context))
                renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
            }
        })
    }
}
