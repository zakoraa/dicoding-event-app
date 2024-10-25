package com.raflis.final_submission_1_android_fundamental.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.raflis.final_submission_1_android_fundamental.MainViewModelFactory
import com.raflis.final_submission_1_android_fundamental.data.local.database.SettingPreferences
import com.raflis.final_submission_1_android_fundamental.data.local.database.dataStore
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        val mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(pref))[SettingsViewModel::class.java]

        with(binding) {
            mainViewModel.getThemeSettings()
                .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                    smDarkMode.isChecked = isDarkModeActive
                }

            smDarkMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                mainViewModel.saveThemeSetting(isChecked)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}