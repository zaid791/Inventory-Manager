package com.example.inventorymanager.common

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.inventorymanager.R

class NavigationHelper(private val navController: NavController) {
    // Method to navigate backward
    fun navigateBackward() {
        navController.navigateUp() // This navigates up the back stack
    }

    // Method to navigate with a specific animation
    fun navigateWithAnimation(
        actionId: Int,
        enterAnim: Int = R.anim.slide_in_right,
        exitAnim: Int = R.anim.slide_out_left,
        popEnterAnim: Int = R.anim.slide_in_left,
        popExitAnim: Int = R.anim.slide_out_right,
        popUpTo: String? = null,
        inclusive: Boolean = false
    ) {
        val options = NavOptions.Builder()
            .setEnterAnim(enterAnim)
            .setExitAnim(exitAnim)
            .setPopEnterAnim(popEnterAnim)
            .setPopExitAnim(popExitAnim)
            .build()
        navController.navigate(actionId, null, options)
    }

    fun setupBackPressHandler(fragment: Fragment) {
        fragment.requireActivity().onBackPressedDispatcher.addCallback(
            fragment.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateBackward()
                }
            })
    }

    fun handleBackPressToActivity(activityClass: Class<*>) {
        // Handle the back press to navigate to a specific activity
        val intent = Intent(navController.context, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        navController.context.startActivity(intent)
    }
}