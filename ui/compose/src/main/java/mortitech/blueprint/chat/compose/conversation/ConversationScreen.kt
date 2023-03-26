package mortitech.blueprint.chat.compose.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mortitech.blueprint.chat.data.ChatMessage

@Composable
fun ConversationScreen(
    viewModel: ConversationViewModel
) {
    val messages by viewModel.incomingMessages.collectAsState(initial = emptyList<ChatMessage>())
    val message = remember { mutableStateOf("") }

    ConversationContent(
        messages = messages as List<ChatMessage>,
        message = message.value,
        updateMessage = { message.value = it },
        onSendClick = {
            if (message.value.isNotEmpty()) {
                //messages.add(message.value)
                message.value = ""
            }
        }
    )
}

@Composable
fun ConversationContent(
    messages: List<ChatMessage>,
    message: String,
    updateMessage: (String) -> Unit,
    onSendClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(messages) { message ->
                ConversationMessage(text = message.content, isSentByUser = false)
            }
        }

        UserMessageInput(
            value = message,
            onValueChange = { updateMessage(it) },
            onSend = onSendClick
        )
    }
}

@Composable
private fun ConversationMessages(
    modifier: Modifier = Modifier,
    messages: List<ChatMessage>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(messages) { message ->
            ConversationMessage(text = message.content, isSentByUser = false)
        }
    }
}

@Composable
fun ConversationMessage(text: String, isSentByUser: Boolean) {
    val backgroundColor = if (isSentByUser) Color(0xFF2196F3) else Color.LightGray
    val contentColor = if (isSentByUser) Color.White else Color.Black

    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = "Enter your message") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onSend,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConversationMessagesPreview() {
    ConversationMessages(
        messages = listOf(
            ChatMessage(
                sender = "John Doe",
                content = "Hello, World!",
                time = "12:00:00",
                ChatMessage.MessageType.INCOMING
            ),
            ChatMessage(
                sender = "Me",
                content = "Hello, John!",
                time = "12:01:00",
                ChatMessage.MessageType.OUTGOING
            ),
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ConversationMessagePreview() {
    ConversationMessage(text = "Hello world", isSentByUser = true)
}

@Preview(showBackground = true)
@Composable
private fun UserInputMessagePreview() {
    UserMessageInput(
        value = "",
        onValueChange = {},
        onSend = {}
    )
}

