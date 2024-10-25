package com.raflis.final_submission_1_android_fundamental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raflis.final_submission_1_android_fundamental.data.local.database.SettingPreferences
import com.raflis.final_submission_1_android_fundamental.ui.settings.SettingsViewModel

class MainViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}