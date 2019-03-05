package hu.kts.quickdebug

import android.content.ContentResolver
import android.provider.Settings

class Settings(private val contentResolver: ContentResolver){

    fun isAdbEnabled() = Settings.Global.getInt(contentResolver, Settings.Global.ADB_ENABLED) == 1

    fun enableAdb(enabled: Boolean) = Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, if (enabled) 1 else 0)

    fun setScreenTimeout(millis: Long) = Settings.System.putLong(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT,  millis)

    fun getScreenTimeout() = Settings.System.getLong(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)

    fun disableDontKeepActivities() = Settings.Global.putInt(contentResolver, Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0)
}