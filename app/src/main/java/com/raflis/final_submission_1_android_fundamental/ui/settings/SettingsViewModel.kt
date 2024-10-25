package com.raflis.final_submission_1_android_fundamental.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raflis.final_submission_1_android_fundamental.data.local.database.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getDailyReminderSetting(): LiveData<Boolean> {
        return pref.getDailyReminderSetting().asLiveData()
    }

    fun saveDailyReminderSetting(isEnabled: Boolean) {
        viewModelScope.launch {
            pref.saveDailyReminderSetting(isEnabled)
        }
    }
}