package com.raflis.final_submission_1_android_fundamental.ui.settings

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.raflis.final_submission_1_android_fundamental.MainViewModelFactory
import com.raflis.final_submission_1_android_fundamental.data.local.database.SettingPreferences
import com.raflis.final_submission_1_android_fundamental.data.local.database.dataStore
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentSettingsBinding
import com.raflis.final_submission_1_android_fundamental.util.WorkerUtil
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var settingsViewModel: SettingsViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    requireActivity(),
                    "Notifications permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Notifications permission rejected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        settingsViewModel =
            ViewModelProvider(this, MainViewModelFactory(pref))[SettingsViewModel::class.java]

        workManager = WorkManager.getInstance(requireActivity())

        with(binding) {
            settingsViewModel.getThemeSettings()
                .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                    smDarkMode.isChecked = isDarkModeActive
                }

            smDarkMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingsViewModel.saveThemeSetting(isChecked)
            }

            settingsViewModel.getDailyReminderSetting().observe(viewLifecycleOwner) { isEnabled ->
                smDailyReminder.isChecked = isEnabled
            }

            smDailyReminder.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    startPeriodicTask()
                } else {
                    cancelPeriodicTask()
                }
                lifecycleScope.launch {
                    settingsViewModel.saveDailyReminderSetting(isChecked)
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startPeriodicTask() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        periodicWorkRequest = PeriodicWorkRequest.Builder(WorkerUtil::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "DailyReminderWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->

                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    binding.smDailyReminder.isChecked = true
                    lifecycleScope.launch {
                        settingsViewModel.saveDailyReminderSetting(true)
                    }
                }

                if (workInfo?.state == WorkInfo.State.FAILED) {
                    Toast.makeText(
                        requireActivity(),
                        "Terjadi kesalahan, harap coba lagi!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.id)
        binding.smDailyReminder.isChecked = false
        lifecycleScope.launch {
            settingsViewModel.saveDailyReminderSetting(false)
        }
    }

}