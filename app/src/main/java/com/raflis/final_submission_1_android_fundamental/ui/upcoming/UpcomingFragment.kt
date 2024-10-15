package com.raflis.final_submission_1_android_fundamental.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raflis.final_submission_1_android_fundamental.data.model.EventModel
import com.raflis.final_submission_1_android_fundamental.data.model.EventType
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentUpcomingBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.EventAdapter

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val upcomingViewModel =
            ViewModelProvider(this)[UpcomingViewModel::class.java]

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        upcomingViewModel.upcomingEventList.observe(viewLifecycleOwner) { consumerUpcomingEventlist ->
            setUpcomingEventList(consumerUpcomingEventlist)
        }

        upcomingViewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpcomingEventList(consumerUpcomingEventlist: List<EventModel?>?) {

        val adapter = EventAdapter(consumerUpcomingEventlist ?: emptyList(), EventType.ALL)

        binding.rvUpcoming.adapter = adapter
        binding.rvUpcoming.setHasFixedSize(true)
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext())

        binding.tvNotUpcomingData.visibility = if (consumerUpcomingEventlist.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}