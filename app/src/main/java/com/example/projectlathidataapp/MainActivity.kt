package com.example.projectlathidataapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectlathidataapp.ui.theme.ProjectLathiDataAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectLathiDataAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SplashContent()
                }
            }
        }
    }
}

//preview function
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectLathiDataAppTheme {
        SplashContent()
    }
}
@Composable
fun SplashContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val context = LocalContext.current// we can't use context directly in composable function hence we need this
        Button(onClick = { Toast.makeText(context, "login", Toast.LENGTH_SHORT).show() }) {
            Text(text = "Login")
        }
        Spacer(Modifier.height(16.dp))// space between buttons

        Button(onClick = { Toast.makeText(context, "Register", Toast.LENGTH_LONG).show() }) {
            Text(text = "Register")
        }
    }
}

