package com.raflis.final_submission_1_android_fundamental.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentHomeBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.AdapterViewType
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.EventAdapter
import com.raflis.final_submission_1_android_fundamental.ui.common.view_model.EventViewModelFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var carouselSnapHelper: CarouselSnapHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory: EventViewModelFactory = EventViewModelFactory.getInstance()
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!s.isNullOrEmpty()) {
                        homeViewModel.searchEvents(s.toString())
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

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

    private fun setUpcomingEventList(consumerUpcomingEventlist: List<Event?>?) {

        val adapter =
            EventAdapter(
                consumerUpcomingEventlist ?: emptyList(),
                AdapterViewType.CAROUSEL_HORIZONTAL
            )

        with(binding) {
            rvHomeUpcoming.adapter = adapter
            rvHomeUpcoming.setHasFixedSize(true)
            rvHomeUpcoming.layoutManager = CarouselLayoutManager()

            if (carouselSnapHelper == null) {
                carouselSnapHelper = CarouselSnapHelper()
                carouselSnapHelper?.attachToRecyclerView(rvHomeUpcoming)
            }

            tvNotUpcomingData.visibility = if (consumerUpcomingEventlist.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    }

    private fun setFinishedEventList(consumerFinishedEventlist: List<Event?>?) {

        val adapter = EventAdapter(
            consumerFinishedEventlist ?: emptyList(),
            AdapterViewType.LINEAR_LAYOUT_VERTICAL
        )

        with(binding) {
            rvHomeFinished.adapter = adapter
            rvHomeFinished.setHasFixedSize(true)
            rvHomeFinished.setLayoutManager(object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            })

            tvNotFinishedData.visibility = if (consumerFinishedEventlist.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
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

    private fun setSearchResultEventList(searchResult: List<Event?>?) {
        val adapter =
            EventAdapter(searchResult ?: emptyList(), AdapterViewType.LINEAR_LAYOUT_VERTICAL)

        with(binding) {
            rvSearchResults.adapter = adapter
            rvSearchResults.setHasFixedSize(true)
            rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
            tvNotFound.visibility =
                if (searchResult.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

    }
}