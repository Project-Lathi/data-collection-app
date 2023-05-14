package com.example.projectlathidataapp.Screens.Record


import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.media.session.PlaybackState
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import androidx.lifecycle.viewModelScope
import com.example.projectlathidataapp.ui.theme.PaleGreen2
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


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

            var isRecording by remember { mutableStateOf(false) }
            var timeLeftInSeconds by remember { mutableStateOf(60) }

            val scope = rememberCoroutineScope()

            LaunchedEffect(isRecording) {
                if (isRecording) {
                    while (timeLeftInSeconds > 0) {
                        delay(1000)
                        timeLeftInSeconds--
                    }
                    isRecording = false
                    recorder.stop()
                    // Add code here to upload the recorded audio to Firebase
                    timeLeftInSeconds = 60
                }
            }
            AudioRecordingVisualizer(isRecording = isRecording)
            Button(
                onClick = {
                    if (isRecording) {
                        recorder.stop()
                        Toast.makeText(context,"Recording Stopped", Toast.LENGTH_SHORT).show()
                        isRecording = false
                        timeLeftInSeconds = 60 // reset timer when recording is stopped
                    } else {
                        scope.launch {
                            isRecording = true
                            File(context.cacheDir, "audio.mp3").also {
                                recorder.start(it)
                                Toast.makeText(context,"Recording Started", Toast.LENGTH_SHORT).show()
                                audioFile = it
                            }
                        }
                    }
                },modifier = Modifier
                    .padding(top = 10.dp)
                    .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),contentPadding = PaddingValues(16.dp),
            ) {
                val buttonText = if (isRecording) "Stop recording" else "Start recording"
                val buttonTextWithTime = "$buttonText ($timeLeftInSeconds s)"
                Text(text = buttonTextWithTime, fontWeight = FontWeight.Bold)
            }



            var isPlaying by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    if (isPlaying) {
                        player.stop()
                        Toast.makeText(context,"Playing Audio", Toast.LENGTH_SHORT).show()
                        isPlaying = false
                    } else {
                        player.playFile(audioFile ?: return@Button)
                        Toast.makeText(context,"Playing Audio Stopped", Toast.LENGTH_SHORT).show()
                        isPlaying = true
                    }
                },modifier = Modifier
                    .padding(top = 10.dp)
                    .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),contentPadding = PaddingValues(16.dp),
            ) {
                Text(text = if (isPlaying) "Stop playing" else "Start playing", fontWeight = FontWeight.Bold)
            }






            var uploading by remember { mutableStateOf(false) }
            FirebaseApp.initializeApp(context)
            val storage = Firebase.storage
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val filename = "audio_$timestamp.mp3"
            Button(
                onClick = {
                    val storageRef = storage.reference
                    val audioRef = storageRef.child("audio.mp3/$filename")
                    val audioUri = Uri.fromFile(audioFile ?: return@Button)
                    val uploadTask = audioRef.putFile(audioUri)

                    uploading = true
                    uploadTask.addOnCompleteListener { task ->
                        uploading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "File uploaded successfully!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "File upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                },modifier = Modifier
                    .padding(top = 10.dp)
                    .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(8.dp)),contentPadding = PaddingValues(16.dp),
            ) {
                Text(text = "Upload", fontWeight = FontWeight.Bold)
            }

            if (uploading) {
                CircularProgressIndicator()
            }


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
            { Text(text = "Sign Out", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
        }
    }
}




@Composable
fun AudioRecordingVisualizer(isRecording: Boolean) {
    val maxAmplitude = 32767 // the maximum value of amplitude from MediaRecorder

    val amplitudes = remember { mutableStateListOf<Float>() }
    var isRunning by remember { mutableStateOf(false) }

    val amplitudeScope = rememberCoroutineScope()

    fun startVisualizer() {
        isRunning = true

        amplitudeScope.launch {
            while (isRunning) {
                val amplitude = (0..maxAmplitude).random().toFloat()
                amplitudes.add(amplitude)

                if (amplitudes.size > 50) {
                    amplitudes.removeFirst()
                }

                delay(50)
            }
        }
    }

    fun stopVisualizer() {
        isRunning = false
        amplitudes.clear()
    }

    if (isRecording) {
        LaunchedEffect(isRecording) {
            startVisualizer()
        }
    } else {
        stopVisualizer()
    }

    Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        val amplitudeSize = amplitudes.size
        val graphWidth = size.width / amplitudeSize.toFloat()

        amplitudes.forEachIndexed { index, amplitude ->
            val amplitudeHeight = amplitude / maxAmplitude * size.height
            val x = graphWidth * index
            val y = size.height - amplitudeHeight

            drawLine(
                color = PaleGreen2,
                start = Offset(x, size.height),
                end = Offset(x, y),
                strokeWidth = 4.dp.toPx()
            )
        }
    }
}

