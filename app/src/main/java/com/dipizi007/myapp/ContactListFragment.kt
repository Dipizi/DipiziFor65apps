package com.dipizi007.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dipizi007.myapp.databinding.FragmentContactsBinding

class ContactListFragment : Fragment() {

    private var connector: Connection? = null
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Connection)
            connector = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.app_name)
        binding.cvContact.setOnClickListener {
            connector?.transition("1") //TODO: Убрать хардкод в будущем
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        connector = null
        super.onDetach()
    }

    companion object {
        const val TAG_FOR_CONTACT_LIST = "contactListFragment"
    }
}