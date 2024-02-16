package com.okation.aivideocreator.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.ActivityMainBinding
import com.okation.aivideocreator.inapp.PaymentViewModel
import com.okation.aivideocreator.utils.Constants.GOOGLE_API_KEY
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializePremiumStatus(applicationContext)
        viewModel.isPremium.observe(this) { isPremium ->
            if (isPremium) {
                setNavigationGraph()
            }
        }
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, GOOGLE_API_KEY).build())
    }

    fun setFullscreenFlags() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
    }

    private fun setNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_fragment_nav_graph) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.homeStartHereFragment)
        navController.graph = navGraph
    }
}