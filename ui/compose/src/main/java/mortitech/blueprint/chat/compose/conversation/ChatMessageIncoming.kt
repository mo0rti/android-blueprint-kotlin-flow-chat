package mortitech.blueprint.chat.compose.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mortitech.blueprint.chat.core.R
import mortitech.blueprint.chat.data.ChatMessage

@Composable
fun ChatMessageIncoming(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 32.dp),
        verticalAlignment = Alignment.Top
    ) {
        CircleImage(
            painter = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .background(
                    color = Color(0xFFECEFF1),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.sender,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
            )
            Text(
                text = message.content,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = message.time,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
fun CircleImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .clip(CircleShape)
    )
}


@Preview(showBackground = true)
@Composable
private fun IncomingChatMessagePreview() {
    ChatMessageIncoming(
        message = ChatMessage(
            sender = "John Doe",
            content = "Hello John Doe, how are you?",
            time = "12:00 PM",
            messageType = ChatMessage.MessageType.INCOMING
        )
    )
}