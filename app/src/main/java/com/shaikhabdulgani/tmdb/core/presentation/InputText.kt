package com.shaikhabdulgani.tmdb.core.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shaikhabdulgani.tmdb.ui.theme.ErrorColor
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.White50

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String,
    onTextChange: (String) -> Unit,
) {
    val passwordVisible = remember { mutableStateOf(!isPassword) }
    val isError = errorMessage.isNotBlank()
    val color = if (isError) {
        ErrorColor
    } else {
        White50
    }
    Column(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = label
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(Shapes.fullRoundedCorner)
                .border(
                    width = 2.dp,
                    shape = Shapes.fullRoundedCorner,
                    color = color,
                )
                .padding(start = 5.dp, end = 5.dp),
            value = value,
            onValueChange = onTextChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Transparent,
                focusedContainerColor = Transparent,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
            ),
            maxLines = 1,
            singleLine = true,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            placeholder = {
                Text(text = placeholder, color = White50)
            },
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = label, tint = color)
            },
            trailingIcon = if (isPassword) {
                {
                    val image = if (passwordVisible.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description =
                        if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            imageVector = image,
                            description,
                            tint = White50
                        )
                    }
                }
            } else {
                null
            }
        )
        if (isError) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = errorMessage,
                color = color,
            )
        }
    }

}