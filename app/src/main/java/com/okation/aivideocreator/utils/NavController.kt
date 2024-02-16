package com.okation.aivideocreator.utils

import androidx.navigation.NavController
import com.okation.aivideocreator.R

// NavController için extension fonksiyon
fun NavController.isCurrentDestinationSongGenerationFragment(): Boolean {
    return currentDestination?.id == R.id.songGenerationFragment
}
