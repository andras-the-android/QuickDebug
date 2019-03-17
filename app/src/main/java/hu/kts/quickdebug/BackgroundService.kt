package hu.kts.quickdebug

import android.app.IntentService
import android.content.Intent
import android.preference.PreferenceManager



class BackgroundService: IntentService("backgroundService") {

    private lateinit var repository: Repository
    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        repository = Repository(Settings(contentResolver), PreferenceManager.getDefaultSharedPreferences(applicationContext))
        notification = Notification(applicationContext)
    }

    override fun onHandleIntent(intent: Intent?) {
        repository.onTileClicked(true)
        QuickDebugTileService.requestListeningState(applicationContext)
        //we can't use autocancel because ongoing event flag prevents it from work
        notification.cancel()
    }
}