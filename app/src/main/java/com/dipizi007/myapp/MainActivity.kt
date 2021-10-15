package com.dipizi007.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf

class MainActivity : AppCompatActivity(), Connection {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.place_frag, ContactListFragment(), "contactListFragment")
                .commit()
    }

    override fun transition(id: Int) {
        val bundle = bundleOf(keyForId to id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.place_frag, ContactDetailsFragment(), "contactDetailsFragment")
        supportFragmentManager.findFragmentByTag("contactDetailsFragment")?.arguments = bundle
        supportActionBar?.title = resources.getString(R.string.contact_details_tool_bar_name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.findFragmentByTag("contactListFragment")?.isVisible == true)
            supportActionBar?.title = resources.getString(R.string.app_name)
    }

    companion object{
        const val keyForId = "idKeY"
    }


}