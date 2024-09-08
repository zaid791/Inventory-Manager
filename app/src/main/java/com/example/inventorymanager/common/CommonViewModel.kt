package com.example.inventorymanager.common

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {
    fun startLoading(mainProgressBar: View, mainLayout: View) {
        mainProgressBar.isVisible = true
        mainLayout.isVisible = false
    }

    fun stopLoading(mainProgressBar: View, mainLayout: View) {
        mainProgressBar.isVisible = false
        mainLayout.isVisible = true
    }
}