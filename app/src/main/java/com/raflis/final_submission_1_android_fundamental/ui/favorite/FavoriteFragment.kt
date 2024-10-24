package com.raflis.final_submission_1_android_fundamental.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentFavoriteBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.view_model.ViewModelFactory
import com.raflis.final_submission_1_android_fundamental.ui.event_details.FavoriteEventAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeFavoriteEvents()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = FavoriteEventAdapter()
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeFavoriteEvents() {
        val favoriteViewModel = obtainViewModel()
        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEventList ->
            adapter.setListFavoriteEvents(favoriteEventList ?: emptyList())
            binding.tvNotFavoriteData.visibility = if (favoriteEventList.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun obtainViewModel(): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }
}