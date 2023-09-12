package com.lovejeet.musicplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel  : ViewModel(){
    var musicContentList : MutableLiveData<ArrayList<DataClass>> = MutableLiveData(arrayListOf<DataClass>())
}