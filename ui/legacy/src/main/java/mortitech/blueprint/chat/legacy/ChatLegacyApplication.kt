package mortitech.blueprint.chat.legacy

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class ChatLegacyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}