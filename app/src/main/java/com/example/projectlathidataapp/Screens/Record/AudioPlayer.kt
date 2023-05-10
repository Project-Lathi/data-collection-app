package com.example.projectlathidataapp.Screens.Record

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}