package ktk.wishdroid.expensetracker.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1,
    minHeight: Dp = 56.dp,
    paddingVertical: Dp = 8.dp,
    cornerRadius: Dp = 12.dp,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    placeholderColor: Color = Color.Gray
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        val contentAlignment = if (minHeight >= 100.dp) Alignment.TopStart else Alignment.CenterStart

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = minHeight)
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(cornerRadius)
                        )
                        .padding(horizontal = 16.dp, vertical = paddingVertical),
                    contentAlignment = contentAlignment
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = placeholderColor,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
