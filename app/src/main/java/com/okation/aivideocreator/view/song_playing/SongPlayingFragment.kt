package com.okation.aivideocreator.view.song_playing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentSongPlayingBinding
import com.okation.aivideocreator.utils.StringConstants
import com.okation.aivideocreator.utils.navigateIfCurrentDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongPlayingFragment : Fragment() {
    private var _binding: FragmentSongPlayingBinding? = null
    private val binding get() = _binding!!
    val args: SongPlayingFragmentArgs by navArgs()
    private var vaw_audio: String = ""
    var mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    private var isPlaying = false
    private lateinit var sharedPreferences: SharedPreferences
    var userName = ""
    var userImage = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences(
                StringConstants.inferenceJobToken,
                Context.MODE_PRIVATE
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(34)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vaw_audio = args.vawAudio
        userImage = args.image
        userName = args.personname
        binding.apply {
            tvKanye.text = userName
            personImage.setImageResource(userImage)
        }
        val url = StringConstants.playUrl + vaw_audio
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()
        musicPlayerInitialze()
        onItemClick(url)
    }

    private fun onItemClick(url: String) {
        binding.apply {
            btnPlay.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    pauseMusic()
                } else {
                    playMusic()
                }

                mediaPlayer.setOnCompletionListener {
                    isPlaying = false
                    binding.btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                }
            }
            btnSkipBackward.setOnClickListener {
                rewindMusic()
            }
            btnSkipForward.setOnClickListener {
                forwardMusic()
            }
            imgBackArrow.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.songPlayingFragment,
                    R.id.action_songPlayingFragment_to_homeStartHereFragment
                )
            }
            btnShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = StringConstants.shareType
                    putExtra(Intent.EXTRA_STREAM, Uri.parse(url))
                    putExtra(Intent.EXTRA_TITLE, StringConstants.shareTitle)
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                val chooser = Intent.createChooser(shareIntent, null)
                startActivity(chooser)
            }
        }
    }

    private fun playMusic() {
        mediaPlayer.start()
        isPlaying = true
        binding.btnPlay.setImageResource(R.drawable.baseline_pause_24)
        updateSeekBar()
    }

    private fun pauseMusic() {
        mediaPlayer.pause()
        isPlaying = false
        binding.btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
    }

    private fun musicPlayerInitialze() {
        binding.seekBar.max = mediaPlayer.duration
        mediaPlayer.setOnPreparedListener {
            val duration = mediaPlayer.duration
            val minutes = duration / 1000 / 60
            val seconds = duration / 1000 % 60
            binding.tvFinishTime.text =
                String.format(StringConstants.seekbarFormat, minutes, seconds)
            updateSeekBar()
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    private fun updateSeekBar() {
        binding.seekBar.progress = mediaPlayer.currentPosition
        val minutes = mediaPlayer.currentPosition / 1000 / 60
        val seconds = mediaPlayer.currentPosition / 1000 % 60
        binding.tvStartTime.text = String.format(StringConstants.seekbarFormat, minutes, seconds)
        if (mediaPlayer.isPlaying) {
            handler.postDelayed({ updateSeekBar() }, 1000)
        }
    }

    private fun rewindMusic() {
        val currentPosition = mediaPlayer.currentPosition
        val rewindDuration = 15000
        val newPosition =
            if (currentPosition - rewindDuration > 0) currentPosition - rewindDuration else 0
        mediaPlayer.seekTo(newPosition)
    }

    private fun forwardMusic() {
        val currentPosition = mediaPlayer.currentPosition
        val newPosition =
            if (currentPosition + 15000 < mediaPlayer.duration) currentPosition + 15000 else mediaPlayer.duration
        mediaPlayer.seekTo(newPosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        _binding = null
    }
}