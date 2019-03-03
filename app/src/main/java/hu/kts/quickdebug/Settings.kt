package hu.kts.quickdebug

import android.content.ContentResolver
import android.provider.Settings

object Settings {

    fun isAdbEnabled(contentResolver: ContentResolver) = Settings.Global.getInt(contentResolver, Settings.Global.ADB_ENABLED) == 1

    fun enableAdb(contentResolver: ContentResolver, enabled: Boolean) = Settings.Global.putInt(contentResolver, Settings.Global.ADB_ENABLED, if (enabled) 1 else 0)

    fun setScreenTimeout(contentResolver: ContentResolver, millis: Long) = Settings.System.putLong(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT,  millis)

    fun getScreenTimeout(contentResolver: ContentResolver) = Settings.System.getLong(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
}