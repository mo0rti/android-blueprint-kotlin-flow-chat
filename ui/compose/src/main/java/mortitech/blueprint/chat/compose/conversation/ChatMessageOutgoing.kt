package mortitech.blueprint.chat.compose.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mortitech.blueprint.chat.data.ChatMessage

@Composable
fun ChatMessageOutgoing(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 32.dp, end = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
                .background(
                    color = Color(0xFF2F80ED),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.content,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.End)
            )
            Text(
                text = message.time,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OutgoingChatMessagePreview() {
    ChatMessageOutgoing(
        message = ChatMessage(
            sender = "John Doe",
            content = "Hello John Doe, how are you?",
            time = "12:00 PM",
            messageType = ChatMessage.MessageType.INCOMING
        )
    )
}