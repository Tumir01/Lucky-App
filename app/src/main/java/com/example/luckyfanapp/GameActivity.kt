package com.example.luckyfanapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityGameBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var roundTime: Int = 10000
    private var difficulty: String? = "medium"

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            "background" -> {
                val savedBackground = sharedPreferences.getInt("background", R.drawable.main_screen_1)
                bindingClass.gameLinearLayout.setBackgroundResource(savedBackground)
            }
            "time" -> {
                roundTime = sharedPreferences.getInt("time", 10000)
                resetTimer()
                startTimer()
            }
            "difficulty" -> {
                difficulty = sharedPreferences.getString("difficulty", "medium")
                startNewRound()
            }
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
        difficulty = sharedPreferences.getString("difficulty", "medium")

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

        val colors = when (difficulty) {
            "low" -> generateRandomColors(4, targetColor)
            "medium" -> generateRandomColors(9, targetColor)
            "hard" -> generateRandomColors(16, targetColor)
            else -> generateRandomColors(9, targetColor)
        }.apply { shuffle() }

        when (difficulty) {
            "low" -> {
                bindingClass.colorGrid.rowCount = 2
                bindingClass.colorGrid.columnCount = 2
                val buttonSize = 400
                for (color in colors) {
                    val colorButton = Button(this).apply {
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = buttonSize
                            height = buttonSize
                            setMargins(22, 22, 22, 22)
                        }
                        setBackgroundColor(color)
                        setOnClickListener { onColorClicked(color) }
                    }
                    bindingClass.colorGrid.addView(colorButton)
                }
            }
            "medium" -> {
                bindingClass.colorGrid.rowCount = 3
                bindingClass.colorGrid.columnCount = 3
                val buttonSize = resources.getDimensionPixelSize(R.dimen.grid_button_size)
                for (color in colors) {
                    val colorButton = Button(this).apply {
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = buttonSize
                            height = buttonSize
                            setMargins(14, 14, 14, 14)
                        }
                        setBackgroundColor(color)
                        setOnClickListener { onColorClicked(color) }
                    }
                    bindingClass.colorGrid.addView(colorButton)
                }
            }
            "hard" -> {
                bindingClass.colorGrid.rowCount = 4
                bindingClass.colorGrid.columnCount = 4
                val buttonSize = 200
                for (color in colors) {
                    val colorButton = Button(this).apply {
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = buttonSize
                            height = buttonSize
                            setMargins(8, 8, 8, 8)
                        }
                        setBackgroundColor(color)
                        setOnClickListener { onColorClicked(color) }
                    }
                    bindingClass.colorGrid.addView(colorButton)
                }
            }
        }
    }

    private fun onColorClicked(color: Int) {
        if (color == targetColor) {
            score++
            bindingClass.scoreText.text = "${getString(R.string.score)} $score"
            startNewRound()
        } else {
            gameOver()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(roundTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                bindingClass.timerTV.text = "${getString(R.string.time)} ${millisUntilFinished / 1000}"
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
        bindingClass.timerTV.text = getString(R.string.end_time_text)
        bindingClass.scoreText.text = "${getString(R.string.end_score_text)} $score"
        bindingClass.targetColorView.setBackgroundColor(Color.TRANSPARENT)
        bindingClass.colorGrid.removeAllViews()
    }

    private fun generateRandomColor(): Int {
        val rnd = Random.Default
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun generateRandomColors(count: Int, targetColor: Int): MutableList<Int> {
        val colors = mutableListOf(targetColor)
        while (colors.size < count) {
            val newColor = generateRandomColor()
            if (newColor != targetColor) {
                colors.add(newColor)
            }
        }
        return colors
    }
}
