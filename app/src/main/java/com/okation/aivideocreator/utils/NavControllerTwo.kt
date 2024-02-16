package com.okation.aivideocreator.utils

import androidx.navigation.NavController

fun NavController.navigateIfCurrentDestination(currentDestinationId: Int, actionId: Int) {
    if (currentDestination?.id == currentDestinationId) {
        navigate(actionId)
    }
}