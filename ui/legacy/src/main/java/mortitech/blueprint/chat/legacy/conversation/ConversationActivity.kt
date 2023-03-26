package mortitech.blueprint.chat.legacy.conversation

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import mortitech.blueprint.chat.legacy.databinding.ActivityConversationBinding

class ConversationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "mortitech.blueprint.chat.EXTRA_USERNAME"
    }

    private val viewModel: ConversationViewModel by viewModels()
    private lateinit var binding: ActivityConversationBinding
    private val conversationAdapter = ConversationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username.isNullOrBlank()) {
            finish()
            return
        }

        setupRecyclerView()
        setupSendButton(username)
        observeIncomingMessages()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ConversationActivity)
            adapter = conversationAdapter
        }
    }

    private fun setupSendButton(username: String) {
        binding.messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val content = binding.messageEditText.text.toString()
                sendMessage(username, content)
                true
            } else {
                false
            }
        }

        binding.messageInputLayout.setEndIconOnClickListener {
            val content = binding.messageEditText.text.toString()
            sendMessage(username, content)
        }
    }

    private fun sendMessage(username: String, content: String) {
        if (content.isNotBlank() && username.isNotBlank()) {
            viewModel.sendMessage(username, content)
            binding.messageEditText.text?.clear()
        }
    }

    private fun observeIncomingMessages() {
        lifecycleScope.launch {
            viewModel.incomingMessages.collect { message ->
                conversationAdapter.addMessage(message)
                binding.recyclerView.scrollToPosition(conversationAdapter.itemCount - 1)
            }
        }
    }
}
