package com.okation.aivideocreator.view.home_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentHomeStartHereBinding
import com.okation.aivideocreator.activity.SharedViewModel
import com.okation.aivideocreator.view.home_select.FakeYouViewModel
import com.okation.aivideocreator.utils.navigateIfCurrentDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeStartHereFragment : Fragment() {
    private var _binding: FragmentHomeStartHereBinding? = null
    private val binding get() = _binding!!
    private var homeAdapter = HomeAdapter()
    private val viewmodel: FakeYouViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeStartHereBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()
        itemClick()
    }

    private fun itemClick() {
        binding.apply {
            btnGenarate.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.homeStartHereFragment,
                    R.id.action_homeStartHereFragment_to_homeSelectFragment
                )
            }
            imgSetting.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.homeStartHereFragment,
                    R.id.action_homeStartHereFragment_to_settingFragment
                )
            }
            imgAdd.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.homeStartHereFragment,
                    R.id.action_homeStartHereFragment_to_homeSelectFragment
                )
            }
        }

        homeAdapter.onItemClick = {
            sharedViewModel.fakeYouEntitySharedViewModel(it)
            val action =
                HomeStartHereFragmentDirections.actionHomeStartHereFragmentToSongPlayingFragment(
                    it.vaw_audio,
                    it.image,
                    it.name
                )
            findNavController().navigate(action)
        }
    }

    private fun observeData() {

        viewmodel.getFakeYouList().observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.apply {
                    toolbarLayout.visibility = View.VISIBLE
                    stratHereLayout.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    rvLayout.visibility = View.VISIBLE
                    toolbarLayout.visibility = View.VISIBLE
                    imgAdd.visibility = View.VISIBLE
                }
            }
            homeAdapter.differ.submitList(list)
        }
    }

    private fun setUpRv() {

        homeAdapter = HomeAdapter()
        binding.recycleviewRoom.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
            setHasFixedSize(true)
        }
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}