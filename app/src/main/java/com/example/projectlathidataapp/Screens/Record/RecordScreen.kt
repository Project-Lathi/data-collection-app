package com.example.projectlathidataapp.Screens.Record


import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.projectlathidataapp.ui.theme.ProjectLathiDataAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import androidx.lifecycle.viewModelScope


@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    ProjectLathiDataAppTheme {
        RecordScreen(navController = rememberNavController())
    }
}

@Composable
fun RecordScreen(navController: NavController) {

    val context = LocalContext.current
    val recorder = remember { AndroidAudioRecorder(context) }
    val player = remember { AndroidAudioPlayer(context) }
    var audioFile by remember { mutableStateOf<File?>(null) }





//    var userName by remember { mutableStateOf("") }
//    val user = FirebaseAuth.getInstance().currentUser!!.uid
//    val db = Firebase.firestore
//
//    val docRef = db.collection("Users").document(user)
//    docRef.get().addOnSuccessListener{document->
//        if(document!=null)
//        {
//            userName = document.getString("email") ?: ""
//        }
//    }


    LaunchedEffect(Unit) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
    }




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

            ) {

                Button(onClick = {
                    File(context.cacheDir, "audio.mp3").also {
                        recorder.start(it)
                        audioFile = it
                    }
                }) {
                    Text(text = "Start recording")
                }
                Button(onClick = {
                    recorder.stop()
                }) {
                    Text(text = "Stop recording")
                }
                Button(onClick = {
                    player.playFile(audioFile ?: return@Button)
                }) {
                    Text(text = "Play")
                }
                Button(onClick = {
                    player.stop()
                }) {
                    Text(text = "Stop playing")
                }



    //            Text(
    //                text = "Hello, $userName!",
    //                fontSize = 24.sp,
    //                fontWeight = FontWeight.Bold,
    //                modifier = Modifier.padding(bottom = 16.dp)
    //            )

//            Spacer(Modifier.height(16.dp))
//            Button(onClick = {
//                audioFile?.let { file ->
//                    viewModelScope.launch {
//                        uploadFileToStorage(file, navController)
//                    }
//                }
//            }) {
//                Text(text = "Upload")
//            }



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


//
//private suspend fun uploadFileToStorage(file: File, navController: NavController) {
//    val storage = Firebase.storage
//    val storageRef = storage.reference
//    val user = FirebaseAuth.getInstance().currentUser!!
//    val audioRef = storageRef.child("users/${user.uid}/audio.mp3")
//    val uploadTask = audioRef.putFile(Uri.fromFile(file))
//    uploadTask.await()
//    val downloadUrl = audioRef.downloadUrl.await().toString()
//    saveAudioDownloadUrlToFirestore(user.uid, downloadUrl,navController)
//}
//
//private suspend fun saveAudioDownloadUrlToFirestore(uid: String, downloadUrl: String,navController: NavController) {
//    val firestore = Firebase.firestore
//    val userDocRef = firestore.collection("users").document(uid)
//    userDocRef.update("audioUrl", downloadUrl).await()
//    navController.popBackStack()
//}


//@Composable
//fun UserProfileName(uid: String) {
//    val firestore = Firebase.firestore
//    val userDocRef = firestore.collection("Users").document(uid)
//    val userProfileName by rememberFirestoreDocument(userDocRef)
//
//    Text("User profile name: $userDocRef")
//}