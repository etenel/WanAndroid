package com.wls.poke.ui.login

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wls.base.BaseApp
import com.wls.base.entity.ResultState
import com.wls.poke.R
import com.wls.poke.base.myDataStore
import com.wls.poke.base.userInfoEntity
import com.wls.poke.entity.UserEntity
import com.wls.poke.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    registry: () -> Unit,
    forgetPassword: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        login = viewModel::login,
        registry = registry,
        forgetPassword = forgetPassword,
        state = state,
        onBack = onBack,

        )
}

@Composable
internal fun LoginScreen(
    login: (account: String, password: String) -> Unit,
    registry: () -> Unit,
    forgetPassword: () -> Unit,
    state: ResultState<UserEntity>,
    onBack: () -> Unit,

    ) {
    var account by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondColor = MaterialTheme.colorScheme.primaryContainer
//    LaunchedEffect(state) {
//        //            登录成功
//        if (state is ResultState.Success) {
//            if (state.data != null) {
//                userInfoEntity = state.data
//                BaseApp.appContext.myDataStore.edit { mutablePreferences ->
//                    mutablePreferences[stringPreferencesKey("user")] =
//                        Json.Default.encodeToString(state.data)
//                }
//            }
//            onBack()
//        }
//    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .drawWithCache {
                val primaryPath = Path()
                val secondPath = Path()
                val thirdPath = Path()
                val fourPath = Path()
                val width = size.width / 10f
                val height = (size.height) / 10f
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

                thirdPath.addRect(Rect(0f, height * 3, width * 10, height * 10))
                thirdPath.op(secondPath, thirdPath, PathOperation.ReverseDifference)
                thirdPath.op(primaryPath, thirdPath, PathOperation.ReverseDifference)

                fourPath.moveTo(0f, height * 9)
                fourPath.cubicTo(
                    width * 3.5f,
                    height * 10,
                    width * 5,
                    height * 8,
                    width * 10,
                    height * 8
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
            loginText, accountInput,
            passwordInput, forgetPw,
            loginButton, register, loginProgress,
        ) = createRefs()
        var passwordEnabled by rememberSaveable {
            mutableStateOf(false)
        }
        val passwordVisualTransformation by remember {
            mutableStateOf(PasswordVisualTransformation())
        }
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(loginText) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 30.dp, end = 60.dp)
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
                .padding(top = 20.dp, end = 60.dp)
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
                        scope.launch {
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
            .padding(end = 60.dp, top = 5.dp)
            .constrainAs(forgetPw) {
                top.linkTo(passwordInput.bottom)
                end.linkTo(parent.end)
            },
            onClick = { forgetPassword() }) {
            Text(text = stringResource(R.string.forget_password))
        }
        TextButton(modifier = Modifier
            .padding(bottom = 10.dp)
            .height(height = 50.dp)
            .constrainAs(register) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = { registry() }) {
            Text(text = stringResource(R.string.ready_register))
        }
        OutlinedButton(
            modifier = Modifier
                .padding(end = 20.dp, bottom = 20.dp)
                .size(width = 100.dp, height = 50.dp)
                .constrainAs(loginButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = { login(account, password) },
            shape = ShapeDefaults.Small
        ) {
            Text(text = stringResource(id = R.string.login))
        }
        when (state) {
            ResultState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.constrainAs(loginProgress) {
                    centerTo(parent)
                })
            }

            is ResultState.Success -> {
             LaunchedEffect(state) {
                 if (state.data != null) {
                     userInfoEntity = state.data
                     BaseApp.appContext.myDataStore.edit { mutablePreferences ->
                         mutablePreferences[stringPreferencesKey("user")] =
                             Json.Default.encodeToString(state.data)
                     }

                 }
                 onBack()
             }
            }

            else -> {

            }
        }
//        if (state is ResultState.Loading) {
//            CircularProgressIndicator(modifier = Modifier.constrainAs(loginProgress) {
//                centerTo(parent)
//            })
//        }
    }


}


@Preview(device = Devices.PHONE, showSystemUi = true)
@Composable
fun PreLogin() {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreen({ _, _ -> }, {}, {}, state, {})
}