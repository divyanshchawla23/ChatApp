package eu.divyansh.mychat.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.divyansh.mychat.R
import eu.divyansh.mychat.data.AuthViewModel
import eu.divyansh.mychat.data.Result


@Composable
fun LoginScreen(authViewModel: AuthViewModel,
                onNavigateToSignUp: ()-> Unit,
                onSigninSuccess:()-> Unit


) {
    var email by remember { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }
    val result by authViewModel.authResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                authViewModel.login(email, password)
                when (result) {
                    is Result.Success->{
                        onSigninSuccess()
                    }
                    is Result.Error ->{

                    }

                    else -> {

                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Don't have an account? "
                //modifier = Modifier.clickable { }
            )
            Text ("Sign up.",
                modifier = Modifier.clickable { onNavigateToSignUp() },
                color = Color(45,100,246)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {

                Text(
                    modifier = Modifier.padding(top = 8.dp), // Optional padding between existing content and new text
                    text = "a Divyansh Chawla production",
                    color = Color(101, 101, 101),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp


                    )
                )


                Icon(painter = painterResource(id = R.drawable.baseline_copyright_24),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 0.dp, top = 1.dp).size(12.dp)
                )


            }
        }

        
    }
}



