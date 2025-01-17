package com.raflis.final_submission_1_android_fundamental.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentFinishedBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.AdapterViewType
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.EventAdapter
import com.raflis.final_submission_1_android_fundamental.ui.common.view_model.EventViewModelFactory

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory: EventViewModelFactory = EventViewModelFactory.getInstance()
        val finishedViewModel: FinishedViewModel by viewModels {
            factory
        }

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

    private fun setFinishedEventList(consumerFinishedEventlist: List<Event?>?) {

        val adapter = EventAdapter(consumerFinishedEventlist ?: emptyList(), AdapterViewType.LINEAR_LAYOUT_VERTICAL)

        with(binding) {
            rvFinished.adapter = adapter
            rvFinished.setHasFixedSize(true)
            rvFinished.layoutManager = LinearLayoutManager(requireContext())

            tvNotFinishedData.visibility = if (consumerFinishedEventlist.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}