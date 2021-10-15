package com.dipizi007.myapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class ContactListFragment : Fragment() {

    private var connector: Connection? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Connection)
            connector = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         return inflater.inflate(R.layout.fragment_contacts, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardView = view.findViewById<CardView>(R.id.card_view)
        cardView.setOnClickListener{
            connector?.transition(ContactListFragment.id)
        }
    }

    override fun onDetach() {
        super.onDetach()
        connector = null
    }

    companion object {
       const val id = 1
   }




}