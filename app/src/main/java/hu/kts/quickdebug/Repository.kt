package hu.kts.quickdebug

import android.content.SharedPreferences
import java.util.concurrent.TimeUnit

class Repository(private val settings: Settings, private val sharedPreferences: SharedPreferences) {

    fun onTileClicked(tileEnabled: Boolean) {
        settings.enableAdb(!tileEnabled)
        if (tileEnabled) {
            if (settings.getScreenTimeout() == LONG_SCREEN_TIMEOUT) {
                settings.setScreenTimeout(sharedPreferences.getLong(KEY_ORIGINAL_SCREEN_TIMEOUT, DEFAULT_TIMEOUT))
            }
        } else {
            sharedPreferences.edit().putLong(KEY_ORIGINAL_SCREEN_TIMEOUT, settings.getScreenTimeout()).apply()
            settings.setScreenTimeout(LONG_SCREEN_TIMEOUT)
        }
    }

    companion object {
        val LONG_SCREEN_TIMEOUT = TimeUnit.MINUTES.toMillis(60)
        const val KEY_ORIGINAL_SCREEN_TIMEOUT = "originalScreenTimeout"
        val DEFAULT_TIMEOUT = TimeUnit.MINUTES.toMillis(2)
    }



}