package mortitech.blueprint.chat.data

data class ChatMessage(val sender: String, val content: String, val messageType: MessageType) {
    enum class MessageType { INCOMING, OUTGOING }
}