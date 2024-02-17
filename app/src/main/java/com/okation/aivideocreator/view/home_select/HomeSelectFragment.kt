package com.okation.aivideocreator.view.home_select

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentHomeSelectBinding
import com.okation.aivideocreator.activity.SharedViewModel
import com.okation.aivideocreator.model.FakeYouPostRequest
import com.okation.aivideocreator.model.VoiceResponse
import com.okation.aivideocreator.utils.StringConstants
import com.okation.aivideocreator.utils.navigateIfCurrentDestination
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeSelectFragment : Fragment() {
    private var _binding: FragmentHomeSelectBinding? = null
    private val binding get() = _binding!!
    private lateinit var fakeyouadapter: FakeYouAdapter
    private val viewmodel: FakeYouViewModel by viewModels()
    private lateinit var voiceResponseList: ArrayList<VoiceResponse>
    private lateinit var sharedPreferences: SharedPreferences
    var nameList: Array<String> = arrayOf()
    var imageList: Array<Int> = arrayOf()
    var tokenList: Array<String> = arrayOf()
    var Token: String = ""
    var userInputText: String = ""
    var uuid = UUID.randomUUID()
    val sharedViewmodel: SharedViewModel by activityViewModels()
    var adapterCelebrityName: String = ""
    var adapterCelebrityImage: Int = R.drawable.img_trump

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        voiceList()
        observeData()
        onItemClick()
    }

    private fun observeData() {
        sharedPreferences = requireActivity().getSharedPreferences(
            StringConstants.inferenceJobToken, Context.MODE_PRIVATE
        )
        viewmodel.postResponse.observe(viewLifecycleOwner) {
            if (it.inference_job_token.isEmpty().not()) {
                val editor = sharedPreferences.edit()
                editor.putString(StringConstants.inferenceJobToken, it.inference_job_token)
                editor.apply()
                findNavController().navigateIfCurrentDestination(
                    R.id.homeSelectFragment,
                    R.id.action_homeSelectFragment_to_songGenerationFragment
                )
            }
        }
    }

    override fun onDestroyView() {
        viewmodel.postResponse.removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }

    private fun voiceList() {
        binding.recyclerview.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            setHasFixedSize(true)
        }
        nameList = arrayOf(
            getString(R.string.celebrity),
            getString(R.string.celebrity),
            getString(R.string.obama),
            getString(R.string.celebrity),
            getString(R.string.elon),
            getString(R.string.celebrity),
            getString(R.string.leo),
            getString(R.string.celebrity),
            getString(R.string.celebrity),
            getString(R.string.celebrity)
        )

        imageList = arrayOf(
            R.drawable.img_trump,
            R.drawable.img_bradd,
            R.drawable.img_obama,
            R.drawable.img_celebrity_one,
            R.drawable.img_taylor,
            R.drawable.img_celebrity_two,
            R.drawable.img_leon,
            R.drawable.img_trump,
            R.drawable.img_johnny,
            R.drawable.img_trump
        )

        tokenList = arrayOf(
            getString(R.string.token_one),
            getString(R.string.token_two),
            getString(R.string.token_three),
            getString(R.string.token_four),
            getString(R.string.token_five),
            getString(R.string.token_six),
            getString(R.string.token_seven),
            getString(R.string.token_eight),
            getString(R.string.token_nine),
            getString(R.string.token_ten)
        )
        voiceResponseList = arrayListOf()
        getData()
    }

    private fun getData() {

        for (i in imageList.indices) {
            val voiceResponse = VoiceResponse(nameList[i], imageList[i], tokenList[i])
            voiceResponseList.add(voiceResponse)
        }
        fakeyouadapter = FakeYouAdapter(requireContext(), voiceResponseList)
        binding.recyclerview.adapter = fakeyouadapter
    }

    private fun onItemClick() {

        val maxLength = 250
        val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val newLength = (dest.length - (dend - dstart)) + (end - start)
            if (newLength <= maxLength) {
                null
            } else {
                ""
            }
        }
        binding.etWriteYourPrompts.filters = arrayOf(inputFilter)
        binding.btnGenarate.setOnClickListener {

            sharedViewmodel.userInputTextSharedViewModel(binding.etWriteYourPrompts.text.toString())
            userInputText = binding.etWriteYourPrompts.text.toString()
            if (userInputText.isEmpty().not()) {
                viewmodel.getPost(FakeYouPostRequest(uuid.toString(), Token, userInputText))
            }
        }

        fakeyouadapter.onItemClick = {

            Token = it.token
            adapterCelebrityName = it.name
            adapterCelebrityImage = it.image
            sharedViewmodel.nameSharedViewModel(adapterCelebrityName)
            sharedViewmodel.imageSharedViewModel(it.image)
        }

        binding.apply {

            imgBackArrow.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.homeSelectFragment,
                    R.id.action_homeSelectFragment_to_songGenerationFragment
                )
            }
            imgBackArrow.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.homeSelectFragment, R.id.action_homeSelectFragment_to_homeStartHereFragment
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}