package mortitech.blueprint.chat.compose.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import mortitech.blueprint.chat.compose.chat.ChatActivity
import mortitech.blueprint.chat.compose.ui.theme.BlueprintChatTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueprintChatTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginScreen { username ->
                        val intent = Intent(this, ChatActivity::class.java)
                        intent.putExtra(ChatActivity.EXTRA_USERNAME, username)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
