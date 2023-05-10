package com.example.projectlathidataapp.Screens.Record

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}
