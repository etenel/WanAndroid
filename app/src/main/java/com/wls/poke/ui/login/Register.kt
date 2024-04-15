@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)

package com.wls.poke.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wls.base.entity.ResultState
import com.wls.poke.R
import com.wls.poke.ui.login.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterRoute(
    modifier: Modifier = Modifier,
    toLogin:()-> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    RegisterScreen(
        modifier = modifier,
        toLogin=toLogin,
        state = state,
        registry = viewModel::registry
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    state: ResultState<Any?>,
    toLogin:()-> Unit,
    registry: (account: String, password: String,rePassword:String) -> Unit,
) {
    var account by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var rePassword by rememberSaveable {
        mutableStateOf("")
    }
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondColor = MaterialTheme.colorScheme.primaryContainer
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .drawWithCache {
                val primaryPath = Path()
                val width = size.width / 10
                val height = (size.height) / 10
                primaryPath.moveTo(0f, 0f)
                primaryPath.lineTo(0f, height * 4)
                primaryPath.cubicTo(
                    width * 3,
                    height * 4,
                    width * 5,
                    height,
                    width * 10,
                    height * 3
                )
                primaryPath.lineTo(width * 10, 0f)
                val secondPath = Path()
                secondPath.lineTo(0f, height * 4.5f)
                secondPath.cubicTo(
                    width * 5,
                    height * 5,
                    width * 7,
                    height * 3,
                    width * 10,
                    height * 5.5f
                )
                secondPath.lineTo(width * 10, 0f)
                secondPath.op(primaryPath, secondPath, PathOperation.ReverseDifference)
                val thirdPath = Path()
                thirdPath.addRect(Rect(0f, height * 3, width * 10, height * 10))
                thirdPath.op(secondPath, thirdPath, PathOperation.ReverseDifference)
                thirdPath.op(primaryPath, thirdPath, PathOperation.ReverseDifference)
                val fourPath = Path()
                fourPath.moveTo(0f, height * 9)
                fourPath.cubicTo(
                    width * 3.5f,
                    height * 10,
                    width * 5,
                    height * 8,
                    width * 10,
                    height * 8.5f
                )
                fourPath.lineTo(width * 10, height * 10)
                fourPath.lineTo(0f, height * 10)
                fourPath.close()
                onDrawBehind {
                    drawPath(path = primaryPath, color = primaryColor)
                    drawPath(path = secondPath, color = secondColor)
                    drawPath(path = thirdPath, color = Color.White)
                    drawPath(path = fourPath, color = primaryColor)

                }
            }
            .padding(start = 20.dp, end = 20.dp),
    ) {


        val (
            loginText, accountInput,login,
            passwordInput, repeatPassword,
            registerProgress,
            registerButton,
        ) = createRefs()
        var passwordEnabled by rememberSaveable {
            mutableStateOf(false)
        }
        val passwordVisualTransformation by remember {
            mutableStateOf(PasswordVisualTransformation())
        }
        val coroutineScope = rememberCoroutineScope()
        Text(
            text = stringResource(R.string.register),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(loginText) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp, end = 60.dp)
                .fillMaxWidth()
                .constrainAs(accountInput) {
                    top.linkTo(loginText.bottom)
                    start.linkTo(parent.start)
                },
            value = account,
            onValueChange = { value ->
                if (value.length > 20) return@OutlinedTextField
                account = value
            },
            label = {
                Text(
                    text = stringResource(id = R.string.please_input_account),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            singleLine = true,
            shape = ShapeDefaults.Medium,
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp, end = 60.dp)
                .fillMaxWidth()
                .constrainAs(passwordInput) {
                    top.linkTo(accountInput.bottom)
                    start.linkTo(parent.start)
                },

            value = password,
            onValueChange = { value ->
                if (value.length > 15) return@OutlinedTextField
                password = value
            },
            label = {
                Text(
                    text = stringResource(id = R.string.please_input_password),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (!passwordEnabled) {
                        coroutineScope.launch {
                            passwordEnabled = true
                            delay(2000)
                            passwordEnabled = false
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordEnabled) VisualTransformation.None else passwordVisualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = ShapeDefaults.Medium,
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp, end = 60.dp)
                .fillMaxWidth()
                .constrainAs(repeatPassword) {
                    top.linkTo(passwordInput.bottom)
                    start.linkTo(parent.start)
                },

            value = rePassword,
            onValueChange = { value ->
                if (value.length > 15) return@OutlinedTextField
                rePassword = value
            },
            label = {
                Text(
                    text = stringResource(id = R.string.repeat_input_password),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (!passwordEnabled) {
                        coroutineScope.launch {
                            passwordEnabled = true
                            delay(2000)
                            passwordEnabled = false
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordEnabled) VisualTransformation.None else passwordVisualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = ShapeDefaults.Medium,
        )
        TextButton(modifier = Modifier
            .padding(bottom = 10.dp)
            .height(height = 50.dp)
            .constrainAs(login) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {
                toLogin()
            }) {
            Text(text = stringResource(R.string.have_account))
        }
        OutlinedButton(
            modifier = Modifier
                .padding(end = 20.dp, bottom = 20.dp)
                .size(width = 100.dp, height = 50.dp)
                .constrainAs(registerButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = { registry(account, password,rePassword) },
            shape = ShapeDefaults.Small
        ) {
            Text(text = stringResource(id = R.string.register))
        }
        when (state) {
            is ResultState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.constrainAs(registerProgress) {
                    centerTo(parent)
                })
            }
//            注册成功
            is ResultState.Success -> {
                toLogin()
            }
            else ->{
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreRegister() {

    RegisterScreen(
        toLogin = {},
        state = ResultState.Loading,
        registry = { _, _ ,_-> }
    )
}