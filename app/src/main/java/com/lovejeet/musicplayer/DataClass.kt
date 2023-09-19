package com.lovejeet.musicplayer

import java.util.concurrent.TimeUnit

data class DataClass(
    var tittle: String = "",
    var artistName: String = "",
    var duration: String = "",
    var storageLocation: String = "",
    var isPlaying: String = ""
)
fun formatDuration(duration: Long):String {
    var minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    var seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
            minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
    return String.format("$minutes:$seconds")
}
