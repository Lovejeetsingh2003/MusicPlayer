package com.lovejeet.musicplayer


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.lovejeet.musicplayer.databinding.FragmentPlayMusicBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlayMusic_fragment : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var runnable: Runnable
    lateinit var binding: FragmentPlayMusicBinding
    var currentsongIndex: Int = 0
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentPlayMusicBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startTime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
        binding.endTime.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
        binding.seekBar.progress = 0
        seekbarsetup()
        binding.seekBar.max = mainActivity.mediaPlayer.duration
        if (mainActivity.mediaPlayer.isPlaying) {
            binding.tvChosenSongName.setText(mainActivity.musicContent?.tittle)
            mainActivity.mediaPlayer.pause()
            binding.btnPlay.setBackgroundResource(R.drawable.ic_play)
        } else {
            mainActivity.mediaPlayer.start()
            binding.btnPause.setBackgroundResource(R.drawable.baseline_pause_24)

        }
        binding.btnPlay.setOnClickListener {
            if (mainActivity.mediaPlayer.isPlaying) {
                binding.tvChosenSongName.setText(mainActivity.musicContent?.tittle)
                mainActivity.mediaPlayer.pause()
                binding.btnPlay.setBackgroundResource(R.drawable.ic_play)
            } else {
                mainActivity.mediaPlayer.start()
                binding.btnPause.setBackgroundResource(R.drawable.baseline_pause_24)

            }
        }
        binding.btnForward.setOnClickListener{
            backforwardsong(increment = true)
        }
        binding.btnBackward.setOnClickListener {
            backforwardsong(increment =false)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mainActivity.mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit


            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit


        })

    }


    fun seekbarsetup() {
        runnable = Runnable {
            binding.startTime.text =
                formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
            binding.seekBar.progress = mainActivity.mediaPlayer.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }

    fun backforwardsong(increment:Boolean) {
        if (increment) {
            setsongposition(increment=false)
            binding.tvChosenSongName.text=mainActivity.musiclist[currentsongIndex].tittle
            createmediaplayer()


        } else {
            setsongposition(increment = true)
            binding.tvChosenSongName.text=mainActivity.musiclist[currentsongIndex].tittle
            createmediaplayer()

        }
    }
    fun setsongposition(increment: Boolean){
        if (increment )
        {
            if (currentsongIndex==mainActivity.musiclist.size-1)
                currentsongIndex=0

            else ++currentsongIndex
        }else
        {
            if (currentsongIndex==0)
                currentsongIndex=mainActivity.musiclist.size-1
            else --currentsongIndex

        }
    }
    fun createmediaplayer() {
        mainActivity.mediaPlayer.stop()
        mainActivity.mediaPlayer.reset()
        mainActivity.mediaPlayer.setDataSource(mainActivity.musiclist[currentsongIndex].storageLocation)
        mainActivity.mediaPlayer.prepare()
        mainActivity.mediaPlayer.start()
        binding.startTime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
        binding.endTime.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
        binding.seekBar.progress = 0
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayMusic_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayMusic_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}


