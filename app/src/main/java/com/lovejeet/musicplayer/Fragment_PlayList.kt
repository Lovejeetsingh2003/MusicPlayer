package com.lovejeet.musicplayer

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lovejeet.musicplayer.databinding.FragmentPlayListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_PlayList.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_PlayList : Fragment(), MusicClick {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var mainActivity: MainActivity
    lateinit var binding : FragmentPlayListBinding
    lateinit var musicViewModel: MusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAdapter = RecyclerAdapter(this)
        binding.recycler.layoutManager = LinearLayoutManager(mainActivity)
        binding.recycler.adapter = recyclerAdapter
        musicViewModel = ViewModelProvider(mainActivity)[MusicViewModel::class.java]

        musicViewModel.musicContentList.observe(mainActivity){
            recyclerAdapter.updateList(it)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_PlayList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnSongPlayClick(musicContent: DataClass) {
        Log.e("TAG", "musicContent.storageLocation ${musicContent.storageLocation}")
        if(mainActivity.mediaPlayer.isPlaying){
            mainActivity.mediaPlayer.stop()
        }
        mainActivity.mediaPlayer = MediaPlayer.create(mainActivity, Uri.parse(musicContent.storageLocation))
        mainActivity.mediaPlayer.start()
    }
}