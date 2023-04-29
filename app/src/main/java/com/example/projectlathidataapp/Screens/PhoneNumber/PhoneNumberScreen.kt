package com.example.projectlathidataapp.Screens.PhoneNumber

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectlathidataapp.Screen
import com.example.projectlathidataapp.Screens.Register.RegisterViewModel
import com.example.projectlathidataapp.ui.theme.PaleGreen2
import kotlinx.coroutines.launch

@Composable
fun PhoneNumberScreen(navController: NavController,viewModel: PhoneViewModel= hiltViewModel(), activity: Activity) {

//    var UserName by rememberSaveable {mutableStateOf("")}
    var phonenumber by rememberSaveable { mutableStateOf("") }
    val state = viewModel.registerstate.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    Column {
        val context = LocalContext.current
        TopAppBar(
            title = {
                Text(text = "Phone Number", color = Color.Black)
            },
            backgroundColor = MaterialTheme.colors.primary
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // Replace "Your phone number" with the appropriate label for your use case

            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = phonenumber,
                onValueChange = { phonenumber = it },
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "phone",
                        tint = PaleGreen2
                    )
                },
                label = { Text(text = "Phone Number") },
                placeholder = { Text(text = "Enter Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    placeholderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )
            Spacer(Modifier.height(16.dp))


            val Context = LocalContext.current
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        viewModel.createUserWithPhone(phonenumber, Context as Activity)

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
                contentPadding = PaddingValues(16.dp),
            )
            { Text(text = "Verify", fontSize = 18.sp) }

        Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            if (state.value?.isLoading == true){
                CircularProgressIndicator()
            }
        }


        LaunchedEffect(key1 = state.value?.isSuccess){
            scope.launch{
                if (state.value?.isSuccess?.isNotEmpty()==true){
                    val success = state.value?.isSuccess
                    Toast.makeText(context,success, Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.OTP.route)
                }
            }
        }


        LaunchedEffect(key1 = state.value?.isError){
            scope.launch{
                if (state.value?.isError?.isNotEmpty()==true){
                    val error = state.value?.isError
                    Toast.makeText(context,error, Toast.LENGTH_SHORT).show()
                }
            }
        }
            Spacer(Modifier.height(25.dp))

        }
    }
}


