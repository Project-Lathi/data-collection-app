package com.example.projectlathidataapp.Screens.Record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectlathidataapp.Screens.Register.RegisterScreen
import com.example.projectlathidataapp.ui.theme.ProjectLathiDataAppTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    ProjectLathiDataAppTheme {
        RecordScreen(navController = rememberNavController())
    }
}

@Composable
fun RecordScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        Text(text = "Record Screen")
    }

}