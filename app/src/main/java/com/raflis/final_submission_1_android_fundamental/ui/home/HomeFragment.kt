package com.raflis.final_submission_1_android_fundamental.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.raflis.final_submission_1_android_fundamental.data.model.EventModel
import com.raflis.final_submission_1_android_fundamental.data.model.EventType
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentHomeBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.EventAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchView.setupWithSearchBar(binding.searchBar)

        homeViewModel.isGettingUpcomingEvent.observe(viewLifecycleOwner) {
            showUpcomingEventLoading(it)
        }

        homeViewModel.isGettingFinishedEvent.observe(viewLifecycleOwner) {
            showFinishedEventLoading(it)
        }

        homeViewModel.isSearchingEvents.observe(viewLifecycleOwner) {
            showSearchLoading(it)
        }

        homeViewModel.upcomingEventList.observe(viewLifecycleOwner) { consumerUpcomingEventlist ->
            setUpcomingEventList(consumerUpcomingEventlist)
        }

        homeViewModel.finishedEventList.observe(viewLifecycleOwner) { consumerFinishedEventlist ->
            setFinishedEventList(consumerFinishedEventlist)
        }

        binding.searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    homeViewModel.searchEvents(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        homeViewModel.searchResultEventList.observe(viewLifecycleOwner) { eventList ->
            setSearchResultEventList(eventList)
        }

        homeViewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpcomingEventList(consumerUpcomingEventlist: List<EventModel?>?) {

        val adapter =
            EventAdapter(consumerUpcomingEventlist ?: emptyList(), EventType.HOME_UPCOMING)

        binding.rvHomeUpcoming.adapter = adapter
        binding.rvHomeUpcoming.setHasFixedSize(true)
        binding.rvHomeUpcoming.layoutManager = CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(binding.rvHomeUpcoming)

        binding.tvNotUpcomingData.visibility = if (consumerUpcomingEventlist.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setFinishedEventList(consumerFinishedEventlist: List<EventModel?>?) {

        val adapter = EventAdapter(consumerFinishedEventlist ?: emptyList(), EventType.ALL)

        binding.rvHomeFinished.adapter = adapter
        binding.rvHomeFinished.setHasFixedSize(true)
        binding.rvHomeFinished.layoutManager = LinearLayoutManager(requireContext())

        binding.tvNotFinishedData.visibility = if (consumerFinishedEventlist.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showUpcomingEventLoading(isGettingUpcomingEvent: Boolean) {
        binding.progressBarUpcomingHome.visibility =
            if (isGettingUpcomingEvent) View.VISIBLE else View.GONE
    }

    private fun showFinishedEventLoading(isGettingFinishedEvent: Boolean) {
        binding.progressBarFinishedHome.visibility =
            if (isGettingFinishedEvent) View.VISIBLE else View.GONE
    }

    private fun showSearchLoading(isSearching: Boolean) {
        binding.progressBarSearch.visibility = if (isSearching) View.VISIBLE else View.GONE
    }

    private fun setSearchResultEventList(searchResult: List<EventModel?>?) {
        val adapter = EventAdapter(searchResult ?: emptyList(), EventType.ALL)
        binding.rvSearchResults.adapter = adapter
        binding.rvSearchResults.setHasFixedSize(true)
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.tvNotFound.visibility =
            if (searchResult.isNullOrEmpty()) View.VISIBLE else View.GONE
    }
}