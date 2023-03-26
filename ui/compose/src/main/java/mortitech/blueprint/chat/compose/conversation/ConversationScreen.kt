package mortitech.blueprint.chat.compose.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mortitech.blueprint.chat.data.ChatMessage

@Composable
fun ConversationScreen(
    userName: String,
    viewModel: ConversationViewModel,
) {
    val messages = remember { mutableStateListOf<ChatMessage>() }
    val message = remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()

    LaunchedEffect("Key") {
        viewModel.incomingMessages.collect {
            messages.add(it)
            scrollState.scrollToItem(messages.size - 1)
        }
    }

    ConversationContent(
        messages = messages,
        message = message.value,
        updateMessage = { message.value = it },
        onSendClick = {
            if (message.value.isNotEmpty()) {
                viewModel.sendMessage(userName, message.value)
                message.value = ""
            }
        },
        scrollState = scrollState,
    )
}

@Composable
fun ConversationContent(
    messages: List<ChatMessage>,
    message: String,
    updateMessage: (String) -> Unit,
    onSendClick: () -> Unit,
    scrollState: LazyListState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ConversationMessages(
            modifier = Modifier.weight(1f),
            messages = messages,
            scrollState = scrollState
        )

        ChatMessageInput(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            value = message,
            onValueChange = { updateMessage(it) },
            onSend = onSendClick
        )
    }
}

@Composable
private fun ConversationMessages(
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    messages: List<ChatMessage>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        reverseLayout = true,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(messages) { message ->
            if (message.isOutgoing())
                ChatMessageOutgoing(message = message)
            else
                ChatMessageIncoming(message = message)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConversationContentPreview() {
    val messages = listOf(
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
    ConversationContent(
        messages = messages,
        message = "",
        updateMessage = {},
        onSendClick = {},
        scrollState = rememberLazyListState()
    )
}
