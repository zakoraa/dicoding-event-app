package com.raflis.final_submission_1_android_fundamental.ui.finished

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
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentFinishedBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.EventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val finishedViewModel =
            ViewModelProvider(this)[FinishedViewModel::class.java]

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        finishedViewModel.finishedEventList.observe(viewLifecycleOwner) { consumerFinishedEventlist ->
            setFinishedEventList(consumerFinishedEventlist)
        }

        finishedViewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFinishedEventList(consumerFinishedEventlist: List<EventModel?>?) {

        val adapter = EventAdapter(consumerFinishedEventlist ?: emptyList(), EventType.ALL)

        binding.rvFinished.adapter = adapter
        binding.rvFinished.setHasFixedSize(true)
        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())

        binding.tvNotFinishedData.visibility = if (consumerFinishedEventlist.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}