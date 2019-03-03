package hu.kts.quickdebug

import android.content.Context
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import java.util.concurrent.TimeUnit


class QuickDebugTileService: TileService() {

    override fun onStartListening() {
        qsTile.state = if (Settings.isAdbEnabled(contentResolver)) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
        super.onStartListening()
    }

    override fun onClick() {
        val tileEnabled = qsTile.state == Tile.STATE_ACTIVE
        Settings.enableAdb(contentResolver, !tileEnabled)
        if (tileEnabled) {
            if (Settings.getScreenTimeout(contentResolver) == LONG_SCREEN_TIMEOUT) {
                Settings.setScreenTimeout(contentResolver, getSharedPreferences("", Context.MODE_PRIVATE).getLong(KEY_ORIGINAL_SCREEN_TIMEOUT, DEFAULT_TIMEOUT))
            }
        } else {
            getSharedPreferences("", Context.MODE_PRIVATE).edit().putLong(KEY_ORIGINAL_SCREEN_TIMEOUT, Settings.getScreenTimeout(contentResolver)).apply()
            Settings.setScreenTimeout(contentResolver, LONG_SCREEN_TIMEOUT)
        }
        qsTile.state = if (tileEnabled) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
        qsTile.updateTile()
    }

    companion object {
        val LONG_SCREEN_TIMEOUT = TimeUnit.MINUTES.toMillis(60)
        const val KEY_ORIGINAL_SCREEN_TIMEOUT = "originalScreenTimeout"
        val DEFAULT_TIMEOUT = TimeUnit.MINUTES.toMillis(2)
    }

}