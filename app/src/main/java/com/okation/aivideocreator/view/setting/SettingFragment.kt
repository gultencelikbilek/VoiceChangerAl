package com.okation.aivideocreator.view.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentSettingBinding
import com.okation.aivideocreator.utils.StringConstants
import com.okation.aivideocreator.utils.StringConstants.revenucatePremium
import com.okation.aivideocreator.utils.navigateIfCurrentDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onItemClick()

    }

    private fun onItemClick() {
        binding.apply {
            imgBackArrow.setOnClickListener {
                findNavController().navigateIfCurrentDestination(
                    R.id.settingFragment,
                    R.id.action_settingFragment_to_homeStartHereFragment
                )
            }

            tvShareApp.apply {
                val url = StringConstants.tvUrlNeon
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        val sharedPreferences =
            requireActivity().getSharedPreferences(revenucatePremium, Context.MODE_PRIVATE)
        val isPremium = sharedPreferences.getBoolean(StringConstants.revenucateIsPremium, false)
        if (isPremium) {
            binding.tvPremium.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}