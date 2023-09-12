package com.lovejeet.musicplayer

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Selection
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lovejeet.musicplayer.databinding.ActivityMainBinding
import com.lovejeet.musicplayer.databinding.RecyclerviewBinding
import java.time.Duration

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController
    var musicList = ArrayList<DataClass>()
    lateinit var musicViewModel: MusicViewModel
    var mediaPlayer: MediaPlayer = MediaPlayer()
    var permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            //songs
            getSongs()
        }else{
            //alert
            //go to setting
            //exit finish
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.navController)
        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]

        binding.btmBar.setOnItemSelectedListener {
            when(it.itemId){
               R.id.fragmentPlaylist->{
                   navController.navigate(R.id.fragment_PlayList)
               }
                R.id.fragmentPlayMusic->{
                    navController.navigate(R.id.fragment_Play_Music)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onResume() {
        super.onResume()
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED)
        {
            getSongs()
        }else{
            permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    @SuppressLint("Range")
    fun getSongs(){
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC
        val cursor: Cursor? = contentResolver?.query(uri, null,
            selection, null, null)
        if(cursor != null) {
            if (cursor?.moveToFirst() == true) {
                while(cursor.isLast == false) {
                    musicList.add(
                        DataClass(
                            tittle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                            artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            storageLocation = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                        )
                    )
                    cursor.moveToNext()
                }
            }
            musicViewModel.musicContentList.value = musicList
        }

    }
}