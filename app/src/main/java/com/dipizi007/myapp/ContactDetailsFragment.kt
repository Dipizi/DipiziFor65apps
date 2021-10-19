package com.dipizi007.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.dipizi007.myapp.databinding.FragmentContactDetailsBinding

class ContactDetailsFragment : Fragment() {

    private var idContact: String? = null
    private var _binding: FragmentContactDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.contact_details_tool_bar_name)
        idContact = arguments?.getString(KEY_ID)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG_FOR_DETAILS_CONTACT = "contactDetailsFragment"
        private const val KEY_ID = "keyId"

        fun newInstance(id: String) = ContactDetailsFragment().apply {
            arguments = bundleOf(KEY_ID to id)
        }
    }
}