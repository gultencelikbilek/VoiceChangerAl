package com.okation.aivideocreator.view.songeneration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.activity.SharedViewModel
import com.okation.aivideocreator.view.song_playing.SongGenerationViewModel
import com.okation.aivideocreator.model.FakeYouEntity
import com.okation.aivideocreator.view.home_select.FakeYouViewModel
import com.okation.aivideocreator.databinding.FragmentSongGenerationBinding
import com.okation.aivideocreator.utils.StringConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SongGenerationFragment : Fragment() {
    private var _binding: FragmentSongGenerationBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: SongGenerationViewModel by viewModels()
    private val viewmodelFakeYou: FakeYouViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    val sharedViewmodel: SharedViewModel by activityViewModels()
    var userName = ""
    var inputText = ""
    var userImage = -1
    var vawAudioRoom: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences(
                StringConstants.inferenceJobToken,
                Context.MODE_PRIVATE
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongGenerationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        val inferenceJobToken = sharedPreferences.getString(StringConstants.inferenceJobToken, "")
        if (inferenceJobToken != null) {
            viewmodel.getJobToken(inferenceJobToken)
        }
        viewmodel.responseJobToken.observe(viewLifecycleOwner) { state ->
            if (state.job_token.isNullOrEmpty()
                    .not() && state.status == StringConstants.completeSucces
            ) {
                state.maybe_public_bucket_wav_audio_path?.let { audioPath ->
                    if (findNavController().currentDestination?.id == R.id.songGenerationFragment) {
                        navigateAndSaveRoom(audioPath, userImage, userName)
                    }
                }
            }
        }

        sharedViewmodel.apply {
            imageResponse.observe(viewLifecycleOwner) {
                userImage = it
            }

            nameResponse.observe(viewLifecycleOwner) { name ->
                userName = name
            }

            userInputTextResponse.observe(viewLifecycleOwner) { text ->
                inputText = text
            }

            fakeYouEntityResponse.observe(viewLifecycleOwner) { fakeYouEntity ->
                vawAudioRoom = fakeYouEntity.vaw_audio
            }
        }
    }

    private fun navigateAndSaveRoom(audioPath: String, userImage: Int, userName: String) {
        runBlocking {
            val fakeYouEntity = FakeYouEntity(
                0,
                this@SongGenerationFragment.userImage, audioPath,
                this@SongGenerationFragment.userName, inputText
            )
            viewmodelFakeYou.addUser(fakeYouEntity)
            val action =
                SongGenerationFragmentDirections.actionSongGenerationFragmentToSongPlayingFragment(
                    audioPath,
                    this@SongGenerationFragment.userImage,
                    this@SongGenerationFragment.userName
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}