package hu.kts.quickdebug

import android.content.ComponentName
import android.content.Context
import android.preference.PreferenceManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService


class QuickDebugTileService: TileService() {

    private lateinit var settings: Settings
    private lateinit var repository: Repository
    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        settings = Settings(contentResolver)
        repository = Repository(settings, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        notification = Notification(applicationContext)
    }

    override fun onStartListening() {
        qsTile.state = if (settings.isAdbEnabled()) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
        super.onStartListening()
    }

    override fun onClick() {
        val tileEnabled = qsTile.state == Tile.STATE_ACTIVE
        repository.onTileClicked(tileEnabled)
        qsTile.state = if (tileEnabled) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
        qsTile.updateTile()
        if (!tileEnabled) notification.show() else notification.cancel()
    }

    companion object {
        fun requestListeningState(context: Context) {
            TileService.requestListeningState(context, ComponentName(BuildConfig.APPLICATION_ID, QuickDebugTileService::class.java.name))
        }
    }


}