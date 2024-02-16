package com.okation.aivideocreator.inapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.activity.MainActivity
import com.okation.aivideocreator.databinding.FragmentHomeRevenucateBinding
import com.okation.aivideocreator.utils.StringConstants
import com.okation.aivideocreator.utils.StringConstants.revenucatePremium
import com.okation.aivideocreator.utils.navigateIfCurrentDestination
import com.revenuecat.purchases.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeRevenucatFragment : Fragment() {
    private var _binding: FragmentHomeRevenucateBinding? = null
    private val binding get() = _binding!!
    private lateinit var revPackage: Package
    private val viewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeRevenucateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRevenucate()
    }

    private fun onRevenucate() {
        Purchases.sharedInstance.getOfferingsWith({
        }) { offerings ->
            offerings.current?.availablePackages?.forEach {
                println(it)
            }
            offerings.current?.getPackage(StringConstants.revenucateSmall)?.also {
                binding.tvMoney.text = it.product.price.formatted
            }
            Purchases.sharedInstance.getOfferingsWith({
            }) { offerings ->
                binding.btnPremium.setOnClickListener {
                    binding.btnPremium.isChecked = true
                    binding.btnContiune.run {
                        isClickable = true
                        isActivated = true
                        isEnabled = true
                    }
                    offerings.current?.getPackage(StringConstants.revenucateSmall)?.also {
                        revPackage = it
                    }
                }
            }

            binding.btnContiune.setOnClickListener {
                setButtonEnabled(false)
                Purchases.sharedInstance.purchaseWith(
                    PurchaseParams.Builder(requireActivity(), revPackage).build(),
                    onError = { error, _ ->
                        setButtonEnabled(true)
                    },
                    onSuccess = { _, _ ->
                        viewModel.setPremiumStatus(true)
                        val sharedPreferences =
                            requireContext().getSharedPreferences(revenucatePremium, Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean(StringConstants.revenucateIsPremium, true)
                        editor.apply()
                        findNavController().navigateIfCurrentDestination(R.id.homeRevenucatFragment, R.id.action_homeRevenucatFragment_to_homeStartHereFragment)
                    }
                )
            }
        }
        binding.btnClose.setOnClickListener {
            findNavController().navigateIfCurrentDestination(R.id.homeRevenucatFragment,R.id.action_homeRevenucatFragment_to_homeStartHereFragment)
        }

        binding.tvPrivacyPolicy.setOnClickListener {
            val url = StringConstants.tvUrlNeon
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setButtonEnabled(enabled: Boolean) {
        binding.btnContiune.apply {
            isClickable = enabled
            isActivated = enabled
            isEnabled = enabled
        }
    }
    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setFullscreenFlags()
        }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}