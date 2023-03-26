package mortitech.blueprint.chat.compose.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mortitech.blueprint.chat.core.R


@Composable
fun LoginScreen(
    onLogin: (userName: String) -> Unit,
) {
    var username by remember { mutableStateOf("") }

    LoginContent(
        username = username,
        updateUsername = { username = it },
        onLogin = onLogin
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    username: String,
    updateUsername: (String) -> Unit,
    onLogin: (username: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        OutlinedTextField(
            value = username,
            onValueChange = { updateUsername(it) },
            label = { Text(text = stringResource(R.string.login_username_hint)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLogin(username) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.login_button_text))
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.mipmap.watermark),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(
        username = "johndoe",
        updateUsername = {},
        onLogin = {}
    )
}