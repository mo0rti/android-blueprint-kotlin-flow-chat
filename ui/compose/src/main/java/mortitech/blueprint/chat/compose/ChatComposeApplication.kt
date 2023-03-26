package mortitech.blueprint.chat.compose

import android.app.Application
import timber.log.Timber

class ChatComposeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}