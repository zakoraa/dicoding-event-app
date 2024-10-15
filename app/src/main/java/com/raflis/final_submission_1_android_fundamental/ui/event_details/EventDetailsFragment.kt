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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.raflis.final_submission_1_android_fundamental.databinding.FragmentEventDetailsBinding
import com.raflis.final_submission_1_android_fundamental.util.DateFormatter

class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val event = EventDetailsFragmentArgs.fromBundle(arguments as Bundle).event

        Glide.with(requireContext())
            .load(event.imageLogo)
            .into(binding.ivImageEventDetail)
        binding.tvOwnerEventDetails.text = event.ownerName

        binding.tvNameEventDetails.text = event.name
        val beginTimeFormatted = event.beginTime?.let {
            DateFormatter.inputFormat.parse(it)
                ?.let { it1 -> DateFormatter.outputDateFormat.format(it1) } +
                    "\n" + DateFormatter.inputFormat.parse(it)
                ?.let { it1 -> DateFormatter.outputTimeFormat.format(it1) }
        } ?: "00:00 WIB"

        binding.tvTimeEventDetails.text = beginTimeFormatted

        binding.tvRemainingQuotaDataEventDetails.text =
            ((event.quota ?: 0) - (event.registrants ?: 0)).toString()

        binding.tvDescEventDetails.text =  Html.fromHtml(event.description?.trimIndent() ?: "halo", Html.FROM_HTML_MODE_COMPACT)

        binding.ivBackEventDetails.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegisterEventDetails.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(event.link)
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}