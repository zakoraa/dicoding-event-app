package com.raflis.final_submission_1_android_fundamental.ui.event_details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.raflis.final_submission_1_android_fundamental.data.local.entity.FavoriteEvent
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentEventDetailsBinding
import com.raflis.final_submission_1_android_fundamental.ui.common.view_model.ViewModelFactory
import com.raflis.final_submission_1_android_fundamental.util.DateUtil

class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteEventAddDeleteViewModel: FavoriteEventAddDeleteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        favoriteEventAddDeleteViewModel = obtainViewModel()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = EventDetailsFragmentArgs.fromBundle(arguments as Bundle)
        val event = args.event
        val favoriteEvent = args.favoriteEvent

        if (event != null) {
            with(binding) {

                Glide.with(requireContext())
                    .load(event.imageLogo)
                    .into(ivImageEventDetail)

                tvOwnerEventDetails.text = event.ownerName

                tvNameEventDetails.text = event.name
                val beginTimeFormatted = event.beginTime?.let {
                    DateUtil.inputFormat.parse(it)?.let { it1 ->
                        DateUtil.outputDateFormat.format(it1) +
                                "\n" + DateUtil.outputTimeFormat.format(it1)
                    }
                } ?: "00:00 WIB"
                tvTimeEventDetails.text = beginTimeFormatted

                tvRemainingQuotaDataEventDetails.text =
                    ((event.quota ?: 0) - (event.registrants ?: 0)).toString()

                tvDescEventDetails.text =
                    Html.fromHtml(
                        event.description?.trimIndent() ?: "",
                        Html.FROM_HTML_MODE_COMPACT
                    )

                ivBackEventDetails.setOnClickListener {
                    findNavController().navigateUp()
                }

                btnRegisterEventDetails.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(event.link)
                    startActivity(intent)
                }

                favoriteEventAddDeleteViewModel.isFavoriteEvent(event.id)
                    .observe(viewLifecycleOwner) { isFavorite ->
                        ivFavoriteEventDetails.isSelected = isFavorite
                    }

                ivFavoriteEventDetails.setOnClickListener {
                    val newFavoriteEvent = FavoriteEvent(
                        id = event.id,
                        summary = event.summary,
                        mediaCover = event.mediaCover,
                        registrants = event.registrants,
                        imageLogo = event.imageLogo,
                        link = event.link,
                        description = event.description,
                        ownerName = event.ownerName,
                        cityName = event.cityName,
                        quota = event.quota,
                        name = event.name,
                        beginTime = event.beginTime,
                        endTime = event.endTime,
                        category = event.category,
                    )

                    favoriteEventAddDeleteViewModel.toggleFavoriteEvent(newFavoriteEvent)
                }

            }
        }

        if (favoriteEvent != null) {
            with(binding) {

                Glide.with(requireContext())
                    .load(favoriteEvent.imageLogo)
                    .into(ivImageEventDetail)

                tvOwnerEventDetails.text = favoriteEvent.ownerName

                tvNameEventDetails.text = favoriteEvent.name

                val beginTimeFormatted = favoriteEvent.beginTime?.let {
                    DateUtil.inputFormat.parse(it)?.let { it1 ->
                        DateUtil.outputDateFormat.format(it1) +
                                "\n" + DateUtil.outputTimeFormat.format(it1)
                    }
                } ?: "00:00 WIB"
                tvTimeEventDetails.text = beginTimeFormatted

                tvRemainingQuotaDataEventDetails.text =
                    ((favoriteEvent.quota ?: 0) - (favoriteEvent.registrants ?: 0)).toString()

                tvDescEventDetails.text =
                    Html.fromHtml(
                        favoriteEvent.description?.trimIndent() ?: "",
                        Html.FROM_HTML_MODE_COMPACT
                    )

                ivBackEventDetails.setOnClickListener {
                    findNavController().navigateUp()
                }

                btnRegisterEventDetails.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(favoriteEvent.link)
                    startActivity(intent)
                }

                favoriteEventAddDeleteViewModel.isFavoriteEvent(favoriteEvent.id)
                    .observe(viewLifecycleOwner) { isFavorite ->
                        ivFavoriteEventDetails.isSelected = isFavorite
                    }

                ivFavoriteEventDetails.setOnClickListener {
                    val newFavoriteEvent = FavoriteEvent(
                        id = favoriteEvent.id,
                        summary = favoriteEvent.summary,
                        mediaCover = favoriteEvent.mediaCover,
                        registrants = favoriteEvent.registrants,
                        imageLogo = favoriteEvent.imageLogo,
                        link = favoriteEvent.link,
                        description = favoriteEvent.description,
                        ownerName = favoriteEvent.ownerName,
                        cityName = favoriteEvent.cityName,
                        quota = favoriteEvent.quota,
                        name = favoriteEvent.name,
                        beginTime = favoriteEvent.beginTime,
                        endTime = favoriteEvent.endTime,
                        category = favoriteEvent.category,
                    )

                    favoriteEventAddDeleteViewModel.toggleFavoriteEvent(newFavoriteEvent)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtainViewModel(): FavoriteEventAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory)[FavoriteEventAddDeleteViewModel::class.java]
    }

}