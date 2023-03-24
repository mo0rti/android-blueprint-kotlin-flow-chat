package mortitech.blueprint.chat.data

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import mortitech.blueprint.chat.data.Constants.TAG
import kotlin.random.Random

/**
 * The [ChatRepository] class is responsible for managing the chat messages.
 *
 * It provides methods to send and receive chat messages, and exposes a [SharedFlow] of incoming messages.
 * The [incomingMessages] flow emits incoming chat messages as they arrive, and can be observed by clients to display the chat history.
 *
 * The [sendMessage] method sends a chat message to the server. It takes a [username] and [content] as input parameters, and emits a new [ChatMessage] with [MessageType.OUTGOING].
 * The message is sent on the IO coroutine dispatcher to avoid blocking the main thread.
 *
 * The [cancel] method cancels all coroutines launched by the [ChatRepository] instance.
 *
 * The [simulateIncomingMessages] method simulates incoming chat messages for testing purposes. It emits a random chat message every 500-2000 milliseconds until a fixed number of messages is reached.
 * The method is launched on the IO coroutine dispatcher to avoid blocking the main thread.
 */
class ChatRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * A [SharedFlow] of incoming chat messages.
     *
     * The flow emits incoming chat messages as they arrive, and can be observed by clients to display the chat history.
     */
    private val _incomingMessages = MutableSharedFlow<ChatMessage>(replay = 1)
    val incomingMessages: SharedFlow<ChatMessage> = _incomingMessages
        .onEach { Log.d(TAG, "Received incoming message: $it") }
        .catch { e -> Log.e(TAG, "Error collecting incoming messages", e) }
        .flowOn(Dispatchers.Default)
        .shareIn(coroutineScope, SharingStarted.WhileSubscribed(replayExpirationMillis = 0), 1)

    /**
     * Sends a chat message to the server.
     *
     * @param username the username of the sender.
     * @param content the content of the message.
     */
    fun sendMessage(username: String, content: String) {
        coroutineScope.launch {
            _incomingMessages.emit(ChatMessage(username, content, ChatMessage.MessageType.OUTGOING))
        }
    }

    /**
     * Cancels all coroutines launched by the [ChatRepository] instance.
     */
    fun cancel() {
        coroutineScope.cancel()
    }

    init {
        simulateIncomingMessages()
    }

    /**
     * Simulates incoming chat messages for testing purposes.
     *
     * The method emits a random chat message every 500-2000 milliseconds until a fixed number of messages is reached.
     * The messages are generated using a predefined list of senders and phrases, and a random selection of each for each message.
     */
    private fun simulateIncomingMessages() {
        coroutineScope.launch {
            val senders = listOf("Alice", "Bob", "Charlie", "David", "Eve")
            val phrases = listOf(
                "Hey, how are you doing?",
                "Did you hear about the new restaurant downtown?",
                "I'm running late for our meeting, sorry!",
                "What do you think about the new project?",
                "Can you send me the report by EOD?",
                "Did you see the game last night?",
                "I'm so excited for the concert tomorrow!",
                "Let's grab lunch sometime this week."
            )
            repeat(1000) {
                delay(Random.nextLong(500, 2000))
                val sender = senders.random()
                val content = phrases.shuffled().take(Random.nextInt(1, 3)).joinToString(separator = " ")
                val message = ChatMessage(sender, content, ChatMessage.MessageType.INCOMING)
                _incomingMessages.emit(message)
            }
        }
    }
}
