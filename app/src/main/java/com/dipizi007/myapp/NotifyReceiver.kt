package com.dipizi007.myapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class NotifyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val id = intent.getIntExtra(Const.CONTACT_ID, -1)
        val nextBirthDay = intent.getStringExtra(Const.BIRTH_DAY)
        val intentMainActivity = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(Const.CONTACT_ID, id)
        }

            val pendingIntent = PendingIntent.getActivity(
                context,
                Const.REQUEST_COD_NOTIFY,
                intentMainActivity,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            val builder = NotificationCompat.Builder(context, Const.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_96dp)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(
                    String.format(
                        "%s %s",
                        context.getString(R.string.notification_text),
                        intent.getStringExtra(Const.CONTACT_NAME)
                    )
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            NotificationManagerCompat.from(context).notify(0, builder)

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val repeatPendingIntent = PendingIntent.getBroadcast(
            context,
            Const.REQUEST_COD_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            nextBirthDay.birthDayToMills(),
            repeatPendingIntent
        )
    }
}

