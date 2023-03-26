package mortitech.blueprint.chat.legacy.conversation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import mortitech.blueprint.chat.data.ChatMessage
import mortitech.blueprint.chat.data.ChatRepository

/**
 * The [ConversationViewModel] class is responsible for managing the chat messages and providing an interface for the UI to interact with the chat.
 *
 * It provides a [sendMessage] method to send chat messages, and exposes a [SharedFlow] of incoming messages.
 * The [incomingMessages] flow emits incoming chat messages as they arrive, and can be observed by clients to display the chat history.
 *
 * The [sendMessage] method delegates to the [ChatRepository] to send the message to the server.
 * The method takes a [username] and [content] as input parameters, and forwards them to the repository for processing.
 *
 * The [onCleared] method cancels all coroutines launched by the [ConversationViewModel] instance.
 */
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
