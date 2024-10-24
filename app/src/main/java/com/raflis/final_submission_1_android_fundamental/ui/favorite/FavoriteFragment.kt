package com.raflis.final_submission_1_android_fundamental.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.raflis.final_submission_1_android_fundamental.data.entity.Event
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentFavoriteBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.AdapterViewType
import com.raflis.final_submission_1_android_fundamental.ui.common.adapter.EventAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setFavoriteEventList(consumerFavoriteEventlist: List<Event?>?) {

        val adapter = EventAdapter(consumerFavoriteEventlist ?: emptyList(), AdapterViewType.LINEAR_LAYOUT_VERTICAL)

        with(binding) {
            rvFavorite.adapter = adapter
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(requireContext())

            tvNotFavoriteData.visibility = if (consumerFavoriteEventlist.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    }

}