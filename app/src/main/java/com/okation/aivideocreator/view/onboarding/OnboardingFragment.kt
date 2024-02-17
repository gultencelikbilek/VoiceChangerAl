package com.okation.aivideocreator.view.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentOnboardingBinding
import com.okation.aivideocreator.utils.StringConstants
import com.okation.aivideocreator.utils.navigateIfCurrentDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
   // private val sharedPreferences =
   //     requireContext().getSharedPreferences(StringConstants.onboardingPassed, Context.MODE_PRIVATE)
   // private val sharedPreferences2 =
   //     requireContext().getSharedPreferences(StringConstants.revenucatePremium, Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContiune.setOnClickListener {
            val sharedPreferences =
                requireContext().getSharedPreferences(StringConstants.isOnboarding, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(StringConstants.isOnboarding, true)
            editor.apply()
            findNavController().navigateIfCurrentDestination(R.id.onboardingFragment,R.id.action_onboardingFragment_to_homeRevenucatFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}