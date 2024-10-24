package com.raflis.final_submission_1_android_fundamental.util

import androidx.recyclerview.widget.DiffUtil
import com.raflis.final_submission_1_android_fundamental.data.entity.FavoriteEvent

class FavoriteEventDiffCallback(
    private val oldFavoriteEventList: List<FavoriteEvent>,
    private val newFavoriteEventList: List<FavoriteEvent>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteEventList.size
    override fun getNewListSize(): Int = newFavoriteEventList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteEventList[oldItemPosition].id == newFavoriteEventList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteEvent = oldFavoriteEventList[oldItemPosition]
        val newFavoriteEvent = newFavoriteEventList[newItemPosition]
        val isEqual =
            oldFavoriteEvent.name == newFavoriteEvent.name
                    && oldFavoriteEvent.id == newFavoriteEvent.id
                    && oldFavoriteEvent.ownerName == newFavoriteEvent.ownerName
                    && oldFavoriteEvent.beginTime == newFavoriteEvent.beginTime
                    && oldFavoriteEvent.endTime == newFavoriteEvent.endTime
                    && oldFavoriteEvent.mediaCover == newFavoriteEvent.mediaCover

        return isEqual
    }
}