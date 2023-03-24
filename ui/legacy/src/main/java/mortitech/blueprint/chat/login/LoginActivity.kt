package mortitech.blueprint.chat.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import mortitech.blueprint.chat.R
import mortitech.blueprint.chat.databinding.ActivityLoginBinding
import mortitech.blueprint.chat.list.ChatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            if (username.isNotEmpty()) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra(ChatActivity.EXTRA_USERNAME, username)
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
