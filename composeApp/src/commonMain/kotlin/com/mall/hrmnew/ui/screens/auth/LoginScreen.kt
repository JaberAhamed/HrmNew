package com.mall.hrmnew.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mall.hrmnew.ui.components.buttons.PrimaryButton
import com.mall.hrmnew.ui.components.inputs.EmailField
import com.mall.hrmnew.ui.components.inputs.PasswordField
import com.mall.hrmnew.ui.theme.Spacing
import com.mall.hrmnew.viewmodel.auth.LoginViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    /**
     * password state is uee for hold password, initially is empty
     */
    var password by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    var showPassword by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val configuration = LocalWindowInfo.current
            val density = LocalDensity.current
            val windowInfo = LocalWindowInfo.current

            // containerSize is in Pixels (IntSize)
            val screenWidthPx = windowInfo.containerSize.width
            val screenHeightPx = windowInfo.containerSize.height

            // Convert Pixels to DP
            val screenWidth = with(density) { screenWidthPx.toDp() }
            val screenHeight = with(density) { screenHeightPx.toDp() }
            val color = MaterialTheme.colorScheme.primary
            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 3)
                        .drawWithCache {
                            onDrawWithContent {
                                val path = Path()
                                path.moveTo(0f, 0f)
                                path.lineTo(0f, size.height)
                                path.cubicTo(
                                    x1 = screenWidth.toPx() / 3,
                                    y1 = size.height + 150.dp.toPx(),
                                    x2 = screenWidth.toPx() / 1.5f,
                                    y2 = size.height - 150.dp.toPx(),
                                    x3 = screenWidth.toPx() + 2.5f,
                                    y3 = size.height
                                )
                                path.lineTo(screenWidth.toPx() + 2f, y = 0f)
                                drawPath(
                                    path = path,
                                    color = color,
                                    style = Fill

                                )
                            }
                        }
                )

                /**
                 * Screen logo section
                 */
                Text(
                    text = "HRM",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                value = email,
                onValueChange = {
                    email = it
                    viewModel.onEmailChange(it.text)
                },
                prefix = {
                    Icon(imageVector = Icons.Outlined.Mail, contentDescription = "Mail icon")
                },
                placeholder = {
                    Text(text = "Email")
                },
                shape = RoundedCornerShape(8.dp),
                isError = uiState.emailError != null,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                value = password,
                onValueChange = {
                    password = it
                    viewModel.onPasswordChange(it.text)
                },
                prefix = {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock icon")
                },
                placeholder = {
                    Text(text = "Password")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        showPassword = !showPassword
                    }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            contentDescription = if (showPassword) "Show Password" else "Hide Password"
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp),
                isError = uiState.passwordError != null,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )

            )

            /**
             * Forgot password text button
             */
            TextButton(
                modifier =
                    Modifier
                        .padding(
                            top = 4.dp,
                            end = 16.dp
                        )
                        .align(Alignment.End),
                onClick = { },
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = 0.dp
                )
            ) {
                Text(
                    text = "Forgot your Password ?",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Show general error if exists
            if (uiState.generalError != null) {
                Text(
                    text = uiState.generalError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Show email error if exists
            if (uiState.emailError != null) {
                Text(
                    text = uiState.emailError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Show password error if exists
            if (uiState.passwordError != null) {
                Text(
                    text = uiState.passwordError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            /**
             * Here is screen Login button
             */
            TextButton(
                modifier =
                    Modifier
                        .padding(
                            top = 10.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(10.dp),
                            ambientColor = Color.Blue.copy(0.7f),
                            spotColor = Color.Blue.copy(0.7f)
                        )
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .height(44.dp)
                        .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    viewModel.onLoginClick(onLoginSuccess)
                },
                enabled = !uiState.isLoading

            ) {
                Text(
                    text = if (uiState.isLoading) "Logging in..." else "Login",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
