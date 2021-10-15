package com.dipizi007.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ContactDetailsFragment : Fragment() {

    var idContact: Int?  = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idContact = arguments?.getInt(MainActivity.keyForId)
        return inflater.inflate(R.layout.fragment_contact_details, container, false)
    }






}