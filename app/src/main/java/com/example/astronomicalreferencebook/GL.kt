package com.example.astronomicalreferencebook

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class GL(private val context: Context) : GLSurfaceView.Renderer {
    private lateinit var square: Square
    private var textureId: Int = 0

    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val mVPMatrix = FloatArray(16)

    private lateinit var sun: Sphere
    private lateinit var planets: List<Sphere>
    private lateinit var planetTextures: IntArray
    private val planetAngles = FloatArray(9)
    private val orbitRadii = floatArrayOf(
        1.0f,  // Mercury
        1.8f,  // Venus
        2.6f,  // Earth
        3.0f,  // Moon (scaled value)
        3.5f,  // Mars
        4.1f,  // Jupiter
        5.8f,  // Saturn
        6.2f,  // Uranus
        6.9f   // Neptune
    )


    private val rotationSpeeds = floatArrayOf(
        2f,  // Mercury
        1.5f, // Venus
        1.2f, // Earth
        1f,  // Mars
        0.9f, // Jupiter
        0.8f, // Saturn
        0.7f, // Uranus
        0.6f, // Neptune
        0.5f  // Pluto
    )
    private val planetRotationSpeeds = FloatArray(9) { 10f }

    private var lineProgram: Int = 0
    private var moonRotationAngle = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glDepthFunc(GLES20.GL_ALWAYS)

        sun = Sphere(radius = 0.6f)
        sun.initialize()

        planets = listOf(
            Sphere(radius = 0.3f),
            Sphere(radius = 0.5f),
            Sphere(radius = 0.5f),
            Sphere(radius = 0.3f),
            Sphere(radius = 0.5f),
            Sphere(radius = 0.5f),
            Sphere(radius = 0.5f),
            Sphere(radius = 0.5f),
            Sphere(radius = 0.5f)
        )

        planets.forEach { it.initialize() }

        planetTextures = IntArray(9)
        planetTextures[0] = loadTexture(context, R.drawable.mercury)
        planetTextures[1] = loadTexture(context, R.drawable.venus)
        planetTextures[2] = loadTexture(context, R.drawable.earth)
        planetTextures[3] = loadTexture(context, R.drawable.moon)
        planetTextures[4] = loadTexture(context, R.drawable.mars)
        planetTextures[5] = loadTexture(context, R.drawable.jupiter)
        planetTextures[6] = loadTexture(context, R.drawable.saturn)
        planetTextures[7] = loadTexture(context, R.drawable.uranus)
        planetTextures[8] = loadTexture(context, R.drawable.neptune)

        textureId = loadTexture(context, R.drawable.sun)

        square = Square(context)
        square.initialize()

        lineProgram = loadLineShaderProgram()
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.scaleM(modelMatrix, 0, 15f, 10f, 1f)
        Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(mVPMatrix, 0, mVPMatrix, 0, modelMatrix, 0)
        square.draw(mVPMatrix)

        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -5f) // Sun at the center
        Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(mVPMatrix, 0, mVPMatrix, 0, modelMatrix, 0)
        sun.draw(mVPMatrix, textureId)

        // Отрисовка планет с орбитами
        for (i in 1 until planets.size) {
            // Отрисовка орбиты
            if (i == 3) continue
            drawOrbit(orbitRadii[i])

            Matrix.setIdentityM(modelMatrix, 0)

            // Вращение планеты вокруг Солнца
            val angle = planetAngles[i]
            val radius = orbitRadii[i]
            val x = radius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = radius * sin(Math.toRadians(angle.toDouble())).toFloat()

            Matrix.translateM(modelMatrix, 0, x, y, -5f) // Позиционирование планеты
            Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
            Matrix.multiplyMM(mVPMatrix, 0, mVPMatrix, 0, modelMatrix, 0)

            // Отрисовка планеты с текстурой
            planets[i].draw(mVPMatrix, planetTextures[i])

            // Обновление угла для орбитального движения
            planetAngles[i] = (planetAngles[i] + rotationSpeeds[i]) % 360

            // Вращение планеты вокруг своей оси
            Matrix.rotateM(modelMatrix, 0, planetAngles[i] * planetRotationSpeeds[i], 0f, 1f, 0f)
            Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
            Matrix.multiplyMM(mVPMatrix, 0, mVPMatrix, 0, modelMatrix, 0)
            planets[i].draw(mVPMatrix, planetTextures[i]) // Повторная отрисовка с учётом вращения

            // Специальный случай для Земли и Луны
            if (i == 2) {
                // Отрисовка Луны
                Matrix.setIdentityM(modelMatrix, 0)

                // Позиционирование Луны на основе положения Земли
                val moonAngle = planetAngles[3]
                val moonRadius = 0.5f
                val moonX = (radius * cos(Math.toRadians(angle.toDouble())).toFloat()) + (moonRadius * cos(Math.toRadians(moonAngle.toDouble())).toFloat())
                val moonY = (radius * sin(Math.toRadians(angle.toDouble())).toFloat()) + (moonRadius * sin(Math.toRadians(moonAngle.toDouble())).toFloat())

                Matrix.translateM(modelMatrix, 0, moonX, moonY, -5f) // Позиционирование Луны
                Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
                Matrix.multiplyMM(mVPMatrix, 0, mVPMatrix, 0, modelMatrix, 0)

                // Вращение Луны вокруг своей оси
                Matrix.rotateM(modelMatrix, 0, moonRotationAngle, 0f, 1f, 0f)
                Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
                Matrix.multiplyMM(mVPMatrix, 0, mVPMatrix, 0, modelMatrix, 0)

                // Отрисовка Луны
                planets[3].draw(mVPMatrix, planetTextures[3])
                planetAngles[3] = (planetAngles[3] + 2f) % 360

                // Обновление угла вращения Луны
                moonRotationAngle = (moonRotationAngle + 1f) % 360
            }
        }
    }
    private fun drawOrbit(radius: Float) {
        val numPoints = 100
        val orbitVertices = FloatArray(numPoints * 2)

        for (i in 0 until numPoints) {
            val angle = Math.toRadians((i * 360.0 / numPoints))
            orbitVertices[i * 2] = (radius * cos(angle)).toFloat()
            orbitVertices[i * 2 + 1] = (radius * sin(angle)).toFloat()
        }

        val orbitBuffer: FloatBuffer = createDirectFloatBuffer(orbitVertices)

        GLES20.glUseProgram(lineProgram)
        val positionHandle = GLES20.glGetAttribLocation(lineProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 99, GLES20.GL_FLOAT, false, 1, orbitBuffer)

        val colorHandle = GLES20.glGetUniformLocation(lineProgram, "vColor")
        GLES20.glUniform4f(colorHandle, 1.0f, 1.0f, 1.0f, 1.0f)

        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, numPoints)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }



    private fun loadLineShaderProgram(): Int {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, readShaderFromFile("line_vertex_shader.glsl"))
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, readShaderFromFile("line_fragment_shader.glsl"))
        val program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)
        GLES20.glLinkProgram(program)
        return program
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }



    private fun createDirectFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = ByteBuffer.allocateDirect(data.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        buffer.put(data)
        buffer.position(0)
        return buffer
    }


    private fun readShaderFromFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val ratio: Float = width.toFloat() / height.toFloat()
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 10f)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1f, 0f)
    }

    // Load texture helper function
    private fun loadTexture(context: Context, resourceId: Int): Int {
        val textureHandle = IntArray(1)

        GLES20.glGenTextures(1, textureHandle, 0)

        if (textureHandle[0] != 0) {
            val options = BitmapFactory.Options()
            options.inScaled = false

            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])

            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR
            )
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR
            )

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

            bitmap.recycle()
        }

        return textureHandle[0]
    }
}