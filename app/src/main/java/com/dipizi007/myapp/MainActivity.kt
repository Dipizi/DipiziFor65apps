package com.dipizi007.myapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), Connection, BindContactService.IService {

    private var bindContactService: BindContactService? = null
    private var bound = false
    private var isNotify = false
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BindContactService.ContactBinder
            bindContactService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bindContactService = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindingService()
        openAfterNotify()
        if (savedInstanceState == null) {
            if (isNotify) {
                openFrag()
                onClickPerson(openAfterNotify())
            } else {
                openFrag()
            }
        }
    }

    override fun getService() = bindContactService

    override fun onClickPerson(contactId: Int) {
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
        if (bound) {
            unbindService(connection)
            bound = false
        }
        super.onDestroy()
    }

    private fun bindingService() {
        val i = Intent(this, BindContactService::class.java)
        bindService(i, connection, Context.BIND_AUTO_CREATE)
    }

    private fun openAfterNotify(): Int {
        val contactId = intent.getIntExtra(Const.CONTACT_ID, -1)
        if (contactId != -1) {
            isNotify = true
        }
        return contactId
    }

    private fun openFrag() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.flContainer,
                ContactListFragment(),
                ContactListFragment.TAG_FOR_CONTACT_LIST
            )
            .commit()
    }
}