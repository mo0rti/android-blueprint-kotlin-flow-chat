package mortitech.blueprint.chat.legacy.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import mortitech.blueprint.chat.core.R
import mortitech.blueprint.chat.legacy.databinding.ActivityLoginBinding
import mortitech.blueprint.chat.legacy.conversation.ConversationActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            if (username.isNotEmpty()) {
                val intent = Intent(this, ConversationActivity::class.java)
                intent.putExtra(ConversationActivity.EXTRA_USERNAME, username)
                startActivity(intent)
                finish()
            } else {
                binding.usernameTextInputLayout.error = getString(R.string.login_username_error)
            }
        }

        binding.usernameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.loginButton.performClick()
                true
            } else {
                false
            }
        }
    }
}
