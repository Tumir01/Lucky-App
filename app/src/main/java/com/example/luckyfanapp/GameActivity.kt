package com.example.luckyfanapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityGameBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var roundTime: Int = 10000
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "background") {
            val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
            bindingClass.gameLinearLayout.setBackgroundResource(savedBackground)
        }
        if (key == "time") {
            roundTime = sharedPreferences.getInt("time", 10000)
        }
        if (key == "difficulty") {
            val difficulty: String? = sharedPreferences.getString("difficulty", "medium")
        }
    }

    private var timer: CountDownTimer? = null

    private var targetColor: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)

        roundTime = sharedPreferences.getInt("time", 10000)

        val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
        bindingClass.gameLinearLayout.setBackgroundResource(savedBackground)

        startNewRound()
    }

    private fun startNewRound() {
        resetTimer()
        startTimer()
        targetColor = generateRandomColor()
        bindingClass.targetColorView.setBackgroundColor(targetColor)

        bindingClass.colorGrid.removeAllViews()

        val colors = generateRandomColors(8, targetColor)
        colors.shuffle()

        for (color in colors) {
            val colorButton = Button(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 250
                    height = 250
                    setMargins(8, 8, 8, 8)
                }
                setBackgroundColor(color)
                setOnClickListener { onColorClicked(color) }
            }
            bindingClass.colorGrid.addView(colorButton)
        }
    }

    private fun onColorClicked(color: Int) {
        if (color == targetColor) {
            score++
            bindingClass.scoreText.text = "Score: $score"
            startNewRound()
        } else {
            gameOver()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(roundTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                bindingClass.timerTV.text = "Time: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                gameOver()
            }
        }
        timer?.start()
    }

    private fun resetTimer() {
        timer?.cancel()
    }

    private fun gameOver() {
        resetTimer()
        bindingClass.timerTV.text = "Time: 0"
        bindingClass.scoreText.text = "Game Over! Final Score: $score"
        bindingClass.targetColorView.setBackgroundColor(Color.TRANSPARENT)
        bindingClass.colorGrid.removeAllViews()
    }

    private fun generateRandomColor(): Int {
        val rnd = Random.Default
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun generateRandomColors(count: Int, targetColor: Int): MutableList<Int> {
        val colors = mutableListOf(targetColor)
        while (colors.size <= count) {
            val newColor = generateRandomColor()
            if (newColor != targetColor) {
                colors.add(newColor)
            }
        }
        return colors
    }
}