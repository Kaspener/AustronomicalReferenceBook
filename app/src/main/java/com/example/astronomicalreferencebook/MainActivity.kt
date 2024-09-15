package com.example.astronomicalreferencebook

import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val glSurfaceView = MyGLSurfaceView(this)
        setContentView(glSurfaceView)
    }
}
