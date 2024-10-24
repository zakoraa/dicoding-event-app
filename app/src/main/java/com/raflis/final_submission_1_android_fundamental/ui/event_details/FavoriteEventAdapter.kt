package com.raflis.final_submission_1_android_fundamental.ui.event_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raflis.final_submission_1_android_fundamental.data.local.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.databinding.EventCardItemBinding
import com.raflis.final_submission_1_android_fundamental.ui.event_details.FavoriteEventAdapter.FavoriteEventViewHolder
import com.raflis.final_submission_1_android_fundamental.ui.home.HomeFragmentDirections
import com.raflis.final_submission_1_android_fundamental.util.DateUtil
import com.raflis.final_submission_1_android_fundamental.util.FavoriteEventDiffCallback

class FavoriteEventAdapter : RecyclerView.Adapter<FavoriteEventViewHolder>() {
    private val listFavoriteEvents = ArrayList<FavoriteEvent>()

    fun setListFavoriteEvents(listFavoriteEvents: List<FavoriteEvent>) {
        val diffCallback = FavoriteEventDiffCallback(this.listFavoriteEvents, listFavoriteEvents)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteEvents.clear()
        this.listFavoriteEvents.addAll(listFavoriteEvents)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding =
            EventCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bind(listFavoriteEvents[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteEvents.size
    }

    inner class FavoriteEventViewHolder(private val binding: EventCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEvent: FavoriteEvent) {
            with(binding) {
                tvName.text = favoriteEvent.name
                tvOwner.text = favoriteEvent.ownerName
                tvTime.text = favoriteEvent.beginTime?.let {
                    favoriteEvent.endTime?.let { it1 ->
                        DateUtil.formatEventTime(
                            it,
                            it1
                        )
                    }
                }

                Glide.with(itemView.context)
                    .load(favoriteEvent.mediaCover)
                    .into(ivEventCardItem)

                btnSeeDetail.setOnClickListener{
                    val action = HomeFragmentDirections.actionNavigationHomeToEventDetailsFragment(null, favoriteEvent)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}