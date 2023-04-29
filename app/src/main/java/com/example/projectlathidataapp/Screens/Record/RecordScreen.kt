package com.example.projectlathidataapp.Screens.Record

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectlathidataapp.Screen
import com.example.projectlathidataapp.Screens.Register.RegisterScreen
import com.example.projectlathidataapp.ui.theme.ProjectLathiDataAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    ProjectLathiDataAppTheme {
        RecordScreen(navController = rememberNavController())
    }
}

@Composable
fun RecordScreen(navController: NavController) {

    Column {
        val context = LocalContext.current
        TopAppBar(
            title = {
                Text(text = "Record Screen", color = Color.Black)
            },
            backgroundColor = MaterialTheme.colors.primary
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ){
            val user = FirebaseAuth.getInstance().currentUser
            val userName = user?.displayName ?: "User"

            Text(text = "Welcome, $userName")
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Login.route)
                    {
                        // Clear the back stack and pop up to the root destination
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid duplicates of the destination in the back stack
                        launchSingleTop = true
                        // Restore saved state if possible
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
                contentPadding = PaddingValues(16.dp),
            )
            { Text(text = "Sign Out", fontSize = 18.sp) }
        }


    }


}