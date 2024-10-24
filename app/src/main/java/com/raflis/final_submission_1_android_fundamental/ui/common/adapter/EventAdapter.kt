package com.raflis.final_submission_1_android_fundamental.ui.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raflis.final_submission_1_android_fundamental.data.local.entity.Event
import com.raflis.final_submission_1_android_fundamental.databinding.EventCardItemBinding
import com.raflis.final_submission_1_android_fundamental.databinding.UpcomingCarouselItemBinding
import com.raflis.final_submission_1_android_fundamental.ui.home.HomeFragmentDirections
import com.raflis.final_submission_1_android_fundamental.util.DateUtil

enum class AdapterViewType {
    CAROUSEL_HORIZONTAL, LINEAR_LAYOUT_VERTICAL
}

class EventAdapter(
    private val eventList: List<Event?>?,
    private val eventType: AdapterViewType
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CAROUSEL_HORIZONTAL = 0
        private const val VIEW_TYPE_LINEAR_LAYOUT_VERTICAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (eventType) {
            AdapterViewType.CAROUSEL_HORIZONTAL -> VIEW_TYPE_CAROUSEL_HORIZONTAL
            AdapterViewType.LINEAR_LAYOUT_VERTICAL -> VIEW_TYPE_LINEAR_LAYOUT_VERTICAL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_CAROUSEL_HORIZONTAL -> {
                val binding = UpcomingCarouselItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeUpcomingEventViewHolder(binding)
            }

            VIEW_TYPE_LINEAR_LAYOUT_VERTICAL -> {
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
        fun bind(event: Event) {

            with(binding) {
                tvUpcomingCarouselItem.text = event.name

                Glide.with(itemView.context)
                    .load(event.mediaCover)
                    .into(ivUpcomingCarouselItem)

                mflUpcomingCarouselItem.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToEventDetailsFragment(event, null)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    inner class AllEventViewHolder(private val binding: EventCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {

            with(binding) {
                tvName.text = event.name
                tvOwner.text = event.ownerName
                tvTime.text = event.beginTime?.let {
                    event.endTime?.let { it1 ->
                        DateUtil.formatEventTime(
                            it,
                            it1
                        )
                    }
                }

                Glide.with(itemView.context)
                    .load(event.mediaCover)
                    .into(ivEventCardItem)

                btnSeeDetail.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToEventDetailsFragment(event, null)
                    it.findNavController().navigate(action)
                }
            }

        }
    }
}