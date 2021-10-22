package com.dipizi007.myapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.dipizi007.myapp.databinding.FragmentContactsBinding

class ContactListFragment : Fragment() {

    private var connector: Connection? = null
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var iService: BindContactService.IService? = null
    private val callback = object : IGetListCallback {
        override fun getListContact(list: List<Person>) {
            _binding?.run {
                requireActivity().runOnUiThread {
                    ivIconList.load(list[0].icon)
                    tvNameList.text = list[0].name
                    tvNumberList.text = list[0].phone1
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Connection)
            connector = context

        if (context is BindContactService.IService)
            iService = context
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
            connector?.onClickPerson(0) //TODO: Убрать хардкод в будущем
        }
        iService?.getService()?.fetchContactList(callback)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        connector = null
        iService = null
        super.onDetach()
    }

    companion object {
        const val TAG_FOR_CONTACT_LIST = "contactListFragment"
    }
}