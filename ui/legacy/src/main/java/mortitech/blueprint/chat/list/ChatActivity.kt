package mortitech.blueprint.chat.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mortitech.blueprint.chat.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var binding: ActivityChatBinding
    private val chatAdapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSendButton()
        observeIncomingMessages()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun setupSendButton() {
        binding.sendButton.setOnClickListener {
            val content = binding.messageEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            if (content.isNotBlank() && username.isNotBlank()) {
                viewModel.sendMessage(username, content)
                binding.messageEditText.text.clear()
            }
        }
    }

    private fun observeIncomingMessages() {
        lifecycleScope.launch {
            viewModel.incomingMessages.collect { message ->
                chatAdapter.addMessage(message)
                binding.recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }
}
