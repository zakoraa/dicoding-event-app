package com.raflis.final_submission_1_android_fundamental.ui.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raflis.final_submission_1_android_fundamental.data.model.EventModel
import com.raflis.final_submission_1_android_fundamental.data.model.EventType
import com.raflis.final_submission_1_android_fundamental.databinding.EventCardItemBinding
import com.raflis.final_submission_1_android_fundamental.databinding.UpcomingCarouselItemBinding
import com.raflis.final_submission_1_android_fundamental.ui.home.HomeFragmentDirections
import com.raflis.final_submission_1_android_fundamental.util.DateFormatter

class EventAdapter(
    private val eventList: List<EventModel?>?,
    private val eventType: EventType
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HOME_UPCOMING = 0
        private const val VIEW_TYPE_ALL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (eventType) {
            EventType.HOME_UPCOMING -> VIEW_TYPE_HOME_UPCOMING
            EventType.ALL -> VIEW_TYPE_ALL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_HOME_UPCOMING -> {
                val binding = UpcomingCarouselItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeUpcomingEventViewHolder(binding)
            }

            VIEW_TYPE_ALL -> {
                val binding = EventCardItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AllEventViewHolder(binding)
            }
            else -> {
                val binding = EventCardItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AllEventViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = eventList?.get(position)
        when (holder) {
            is HomeUpcomingEventViewHolder -> event?.let { holder.bind(it) }
            is AllEventViewHolder -> event?.let { holder.bind(it) }
        }
    }

    override fun getItemCount(): Int = eventList!!.size

    inner class HomeUpcomingEventViewHolder(private val binding: UpcomingCarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventModel) {

            binding.tvUpcomingCarouselItem.text = event.name

            Glide.with(itemView.context)
                .load(event.mediaCover)
                .into(binding.ivUpcomingCarouselItem)

            binding.mflUpcomingCarouselItem.setOnClickListener{
                val action = HomeFragmentDirections.actionNavigationHomeToEventDetailsFragment(event)
                it.findNavController().navigate(action)
            }

        }
    }

    inner class AllEventViewHolder(private val binding: EventCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(event: EventModel) {

            binding.tvName.text = event.name
            binding.tvOwner.text = event.ownerName
            binding.tvTime.text = event.beginTime?.let {
                event.endTime?.let { it1 ->
                    DateFormatter.formatEventTime(
                        it,
                        it1
                    )
                }
            }

            Glide.with(itemView.context)
                .load(event.mediaCover)
                .into(binding.ivEventCardItem)

            binding.btnSeeDetail.setOnClickListener{
                val action = HomeFragmentDirections.actionNavigationHomeToEventDetailsFragment(event)
                it.findNavController().navigate(action)
            }
        }
    }
}