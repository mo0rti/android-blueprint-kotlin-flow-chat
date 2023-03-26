package mortitech.blueprint.chat.compose.conversation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import mortitech.blueprint.chat.data.ChatMessage
import mortitech.blueprint.chat.data.ChatRepository

class ConversationViewModel : ViewModel() {
    private val chatRepository = ChatRepository()

    /**
     * A [SharedFlow] of incoming chat messages.
     *
     * The flow emits incoming chat messages as they arrive, and can be observed by clients to display the chat history.
     */
    val incomingMessages: SharedFlow<ChatMessage> = chatRepository.incomingMessages

    /**
     * Sends a chat message to the server.
     *
     * @param username the username of the sender.
     * @param content the content of the message.
     */
    fun sendMessage(username: String, content: String) {
        chatRepository.sendMessage(username, content)
    }

    /**
     * Cancels all coroutines launched by the [ConversationViewModel] instance.
     */
    override fun onCleared() {
        super.onCleared()
        chatRepository.cancel()
    }
}
