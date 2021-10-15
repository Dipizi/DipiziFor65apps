package com.dipizi007.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dipizi007.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Connection {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.flContainer,
                    ContactListFragment(),
                    ContactListFragment.TAG_FOR_CONTACT_LIST
                )
                .commit()
    }

    override fun transition(contactId: String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.flContainer,
                ContactDetailsFragment.newInstance(contactId),
                ContactDetailsFragment.TAG_FOR_DETAILS_CONTACT
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}