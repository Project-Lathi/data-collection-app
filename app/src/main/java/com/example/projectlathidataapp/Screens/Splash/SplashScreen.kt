package com.example.projectlathidataapp.Screens.Splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectlathidataapp.Screen
import com.example.projectlathidataapp.ui.theme.ProjectLathiDataAppTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectLathiDataAppTheme {
        SplashScreen(navController = rememberNavController())
    }
}
@Composable
fun SplashScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        val context = LocalContext.current// we can't use context directly in composable function hence we need this
        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(text = "Login")
        }
        Spacer(Modifier.height(16.dp))// space between buttons

        Button(
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(text = "Register")
        }
    }
}