package com.example.luckyfanapp

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyfanapp.databinding.ActivityStatisticBinding
import com.example.luckyfanapp.db.MainDb
import com.example.luckyfanapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class StatisticActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityStatisticBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == Constants.PERMISSION_BACKGROUND) {
            val savedBackground = sharedPreferences.getInt(Constants.PERMISSION_BACKGROUND, R.drawable.main_screen_1)
            bindingClass.StatisticLayout.setBackgroundResource(savedBackground)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        val savedBackground = sharedPreferences.getInt(Constants.PERMISSION_BACKGROUND, R.drawable.main_screen_1)
        bindingClass.StatisticLayout.setBackgroundResource(savedBackground)

        if (hasUsageStatsPermission()) {
            appTimeCounter()
        } else {
            requestUsageStatsPermission()
        }
        bindingClass.backStatisticButton.setOnClickListener {
            val intent = Intent(this@StatisticActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        appTimeCounter()
    }

    private fun appTimeCounter() {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val startTime = endTime - 1000 * 60 * 60 * 24

        val usageStatsList: List<UsageStats> = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        var totalTime: Long = 0
        val packageName = applicationContext.packageName

        for (usageStats in usageStatsList) {
            if (usageStats.packageName == packageName) {
                totalTime += usageStats.totalTimeInForeground
            }
        }

        val usageTimeMillis = totalTime
        val usageTimeSeconds = usageTimeMillis / 1000
        val usageTimeText = DateUtils.formatElapsedTime(usageTimeSeconds)

        bindingClass.textView2.text = "Time spent today in the app: $usageTimeText"

        val db = MainDb.getDb(this)
        val androidId = getAndroidId(this)

        CoroutineScope(Dispatchers.IO).launch {
            val existingUser = db.getDao().getUserByAndroidId(androidId)

            if (existingUser != null) {
                existingUser.timeSpentInApp = usageTimeText
                db.getDao().updateUser(existingUser)
            } else {
                val newUser = User(
                    androidId = androidId,
                    timeSpentInApp = usageTimeText
                )
                db.getDao().insertUser(newUser)
            }
        }
    }


    fun clearDatabase(context: Context) {
        context.deleteDatabase("UserDb")
    }

    private fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    private fun hasUsageStatsPermission(): Boolean {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val appStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            System.currentTimeMillis() - 1000 * 60 * 60 * 24,
            System.currentTimeMillis()
        )
        return appStats.isNotEmpty()
    }

    private fun requestUsageStatsPermission() {
        Toast.makeText(this, "Please grant usage stats permission", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }
}