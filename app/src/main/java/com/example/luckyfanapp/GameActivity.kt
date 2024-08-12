package com.example.luckyfanapp

import GameOverFragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.Constants.COUNT_OF_BUTTONS
import com.example.luckyfanapp.Constants.HARD_DIFFICULT
import com.example.luckyfanapp.Constants.LOW_DIFFICULT
import com.example.luckyfanapp.Constants.MEDIUM_DIFFICULT
import com.example.luckyfanapp.Constants.PERMISSION_BACKGROUND
import com.example.luckyfanapp.Constants.PERMISSION_DIFFICULTY
import com.example.luckyfanapp.Constants.PERMISSION_TIME
import com.example.luckyfanapp.Constants.SHARED_PREFERENCES_NAME
import com.example.luckyfanapp.Constants.TEN_SECS_TIMER
import com.example.luckyfanapp.databinding.ActivityGameBinding
import kotlin.math.sqrt
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityGameBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var roundTime: Int = TEN_SECS_TIMER
    private var difficulty: String? = MEDIUM_DIFFICULT
    private var  handler = Handler(Looper.getMainLooper())

    private var timer: CountDownTimer? = null
    private var targetColor: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.backButton.setOnClickListener {
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        roundTime = sharedPreferences.getInt(PERMISSION_TIME, TEN_SECS_TIMER)
        difficulty = sharedPreferences.getString(PERMISSION_DIFFICULTY, LOW_DIFFICULT)

        val savedBackground = sharedPreferences.getInt(PERMISSION_BACKGROUND, R.drawable.main_screen_1)
        bindingClass.gameLinearLayout.setBackgroundResource(savedBackground)

        startNewRound()
    }

    override fun onStop() {
        super.onStop()
        resetTimer()
    }

    private fun startNewRound() {

        resetTimer()
        bindingClass.targetColorView.visibility = View.VISIBLE
        targetColor = generateRandomColor()
        bindingClass.targetColorView.setBackgroundColor(targetColor)
        bindingClass.colorGrid.removeAllViews()

        when (difficulty) {
            LOW_DIFFICULT -> {
                gameButtonsGeneration()
            }

            MEDIUM_DIFFICULT -> {
                gameButtonsGeneration()
                startTimer()
            }

            HARD_DIFFICULT -> {
                bindingClass.timerTV.visibility = View.INVISIBLE
                handler.postDelayed({
                    if (!isFinishing) {
                        bindingClass.timerTV.visibility = View.VISIBLE
                        gameButtonsGeneration()
                        bindingClass.targetColorView.visibility = View.INVISIBLE
                        startTimer()
                    }
                }, 2000)
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
    private fun gameButtonsGeneration() {
        val colors = generateRandomColors(COUNT_OF_BUTTONS, targetColor).apply { shuffle() }
        bindingClass.colorGrid.rowCount = sqrt(COUNT_OF_BUTTONS.toDouble()).toInt()
        bindingClass.colorGrid.columnCount = sqrt(COUNT_OF_BUTTONS.toDouble()).toInt()
        val buttonSize = Constants.BUTTONS_SIZE_MEDIUM

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.game_buttons_appearance)

        for (color in colors) {
            val colorButton = Button(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = buttonSize
                    height = buttonSize
                    setMargins(Constants.MARGINS_FOR_BUTTONS, Constants.MARGINS_FOR_BUTTONS,
                        Constants.MARGINS_FOR_BUTTONS, Constants.MARGINS_FOR_BUTTONS)
                }
                setBackgroundColor(color)
                setOnClickListener { onColorClicked(color) }
            }

            bindingClass.colorGrid.addView(colorButton)
            colorButton.startAnimation(fadeInAnimation)
        }
    }

    private fun resetTimer() {
        timer?.cancel()
    }

    private fun gameOver() {
        bindingClass.apply {
        scoreText.text = "${getString(R.string.end_score_text)} $score"
        targetColorView.setBackgroundColor(Color.TRANSPARENT)
        hatLinearLayout.visibility = View.INVISIBLE
        selectTheColorTV.visibility = View.INVISIBLE
        colorGrid.visibility = View.GONE
        supportFragmentManager.beginTransaction().replace(R.id.gameLinearLayout, GameOverFragment()).commit()
        }
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

object Constants {
    const val MARGINS_FOR_BUTTONS = 16
    const val LOW_DIFFICULT = "low"
    const val MEDIUM_DIFFICULT = "medium"
    const val HARD_DIFFICULT = "hard"
    const val BUTTONS_SIZE_MEDIUM = 260
    const val FIVE_SECS_TIMER = 5000
    const val TEN_SECS_TIMER = 10000
    const val TWENTY_SECS_TIMER = 20000
    const val PERMISSION_BACKGROUND = "background"
    const val PERMISSION_TIME = "time"
    const val PERMISSION_DIFFICULTY = "difficulty"
    const val COUNT_OF_BUTTONS = 9
    const val SHARED_PREFERENCES_NAME = "MyAppPreferences"
}
