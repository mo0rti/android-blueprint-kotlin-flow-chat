package mortitech.blueprint.chat.compose.conversation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import mortitech.blueprint.chat.compose.ui.theme.BlueprintChatTheme

class ConversationActivity : ComponentActivity() {

    companion object {
        const val EXTRA_USERNAME = "mortitech.blueprint.chat.EXTRA_USERNAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username.isNullOrBlank()) {
            finish()
            return
        }

        setContent {
            BlueprintChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ConversationScreen(
                        viewModel = ConversationViewModel(),
                        userName = username
                    )
                }
            }
        }
    }
}
