package com.example.projectlathidataapp.Screens.Login


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectlathidataapp.Screen
import com.example.projectlathidataapp.ui.theme.PaleGreen2
import com.example.projectlathidataapp.ui.theme.ProjectLathiDataAppTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    ProjectLathiDataAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val username= remember {mutableStateOf("")}
    val password= remember {mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        val context = LocalContext.current
        OutlinedTextField(
            value = username.value,
            onValueChange = {username.value=it},
            leadingIcon = { Icon(Icons.Default.Person, contentDescription ="person" , tint = PaleGreen2)},
            label = { Text(text = "Email")},
            placeholder = { Text(text = "Enter Email")},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                placeholderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black
            )
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = {password.value=it},
            leadingIcon = { Icon(Icons.Default.Info, contentDescription ="person" , tint = PaleGreen2)},
            label = { Text(text = "Password")},
            placeholder = { Text(text = "Enter Password")},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                placeholderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black
            )
        )

        Button(
            onClick = { Toast.makeText(context, "login", Toast.LENGTH_SHORT).show() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
            contentPadding = PaddingValues(16.dp),
        )
        { Text(text = "Login", fontSize = 18.sp)}

        Spacer(Modifier.height(25.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append("Don't have Account")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" Register")}
            },
            color = Color.Blue,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .wrapContentSize(align = Alignment.Center)
                .clickable { navController.navigate(Screen.Register.route){
                    // Clear the back stack and pop up to the root destination
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid duplicates of the destination in the back stack
                    launchSingleTop = true
                    // Restore saved state if possible
                    restoreState = true
                } }
        )
    }
}


