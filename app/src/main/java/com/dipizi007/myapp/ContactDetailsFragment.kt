package com.dipizi007.myapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
    private var birthday: String? = null
    private val callback = object : IGetDetailsCallback {

        override fun getDetailsContact(contact: Person) {
            _binding?.run {
                requireActivity().runOnUiThread {
                    ivIconDetails.load(contact.icon)
                    tvNameDetails.text = contact.name
                    tvNumberDetails.text = contact.phone1
                    tvEmailDetails.text = contact.email1
                    tvBirthDay.text = contact.birthDay
                }
                birthday = contact.birthDay
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
        createChannel()
        requireActivity().title = getString(R.string.contact_details_tool_bar_name)
        idContact = arguments?.getInt(KEY_ID)
        idContact?.let { iService?.getService()?.getContactBy(callback, it) }
        if (checkStatus()) {
            binding.btnBirthDay.text = getString(R.string.button_unable_birthday_text)
            binding.btnBirthDay.setOnClickListener {
                closeAlarm()
            }
        } else {
            binding.btnBirthDay.text = getString(R.string.button_enable_birthday_text)
            binding.btnBirthDay.setOnClickListener {
                createAlarm()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        iService = null
        super.onDetach()
    }

    private fun createAlarm() {
        val intent = setIntent().apply {
            putExtra(Const.CONTACT_NAME, binding.tvNameDetails.text)
            putExtra(Const.CONTACT_ID, idContact)
            putExtra(Const.BIRTH_DAY, birthday)
        }
        binding.btnBirthDay.text =
            getString(R.string.button_unable_birthday_text)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val checkIntent = PendingIntent.getBroadcast(
            context,
            Const.REQUEST_COD_ALARM,
            intent,
            PendingIntent.FLAG_NO_CREATE
        )
        if (checkIntent == null) {
            val pendingIntent = setPendingIntent(intent)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                birthday.birthDayToMills(),
                pendingIntent
            )
        }
    }

    private fun closeAlarm() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        binding.btnBirthDay.text =
            getString(R.string.button_enable_birthday_text)
        val intentPending = setPendingIntent(setIntent())
        alarmManager.cancel(setPendingIntent(setIntent()))
        intentPending.cancel()
    }

    private fun setIntent(): Intent {
        val intent = Intent(context, NotifyReceiver::class.java).apply {
            action = NOTIFICATION_ACTION
        }
        return intent
    }

    private fun setPendingIntent(intent: Intent): PendingIntent {
        val pendingIntentBroadcast = PendingIntent.getBroadcast(
            context,
            Const.REQUEST_COD_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return pendingIntentBroadcast
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Const.CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun checkStatus(): Boolean {
        val intent = setIntent()
        val checkIntent =
            PendingIntent.getBroadcast(
                context,
                Const.REQUEST_COD_ALARM,
                intent,
                PendingIntent.FLAG_NO_CREATE
            )
        return checkIntent != null
    }

    companion object {
        const val TAG_FOR_DETAILS_CONTACT = "contactDetailsFragment"
        private const val KEY_ID = "keyId"
        private const val CHANNEL_NAME = "channelName"
        private const val NOTIFICATION_ACTION = "com.dipizi007.myapp"

        fun newInstance(id: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(KEY_ID to id)
        }
    }
}

