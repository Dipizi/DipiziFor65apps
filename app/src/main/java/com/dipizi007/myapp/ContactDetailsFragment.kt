package com.dipizi007.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.dipizi007.myapp.databinding.FragmentContactDetailsBinding

class ContactDetailsFragment : Fragment() {

    private var idContact: Int? = null
    private var _binding: FragmentContactDetailsBinding? = null
    private val binding get() = _binding!!
    private var iService: BindContactService.IService? = null
    private val callback = object : IGetDetailsCallback {
        override fun getDetailsContact(contact: Person) {
            _binding?.run {
                requireActivity().runOnUiThread {
                    ivIconDetails.load(contact.icon)
                    tvNameDetails.text = contact.name
                    tvNumberDetails.text = contact.phone1
                    tvEmailDetails.text = contact.email1
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BindContactService.IService)
            iService = context
    }

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
        idContact = arguments?.getInt(KEY_ID)
        idContact?.let { iService?.getService()?.getContactBy(callback, it) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        iService = null
        super.onDetach()
    }

    companion object {
        const val TAG_FOR_DETAILS_CONTACT = "contactDetailsFragment"
        private const val KEY_ID = "keyId"

        fun newInstance(id: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(KEY_ID to id)
        }
    }
}