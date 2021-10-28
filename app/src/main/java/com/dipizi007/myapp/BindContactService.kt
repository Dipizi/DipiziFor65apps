package com.dipizi007.myapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class BindContactService : Service() {

    private val binder = ContactBinder()
    private val listPerson = listOf(
        Person(
            "https://sun1-16.userapi.com/s/v1/ig2/74hs5dlKMafNHHwSG6eGKRUsmKQcKZm3U9i4e5CLMdEFxaTCamgzQ8dfbwnf4Yqlb_DGbH92gRCfgXyvA7EZgOVg.jpg?size=200x200&quality=96&crop=143,182,347,347&ava=1",
            "8-999-851-97-22",
            "Kapibara@yandex.ru",
            "Капибара",
            0 //TODO: Убрать весь хардкод в будущем
        )
    )

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun fetchContactList(callback: IGetListCallback) {
        val weakReferenceCallback = WeakReference(callback)
        thread {
            weakReferenceCallback.get()?.getListContact(listPerson)
        }
    }

    fun getContactBy(callback: IGetDetailsCallback, id: Int) {
        val weakReferenceCallback = WeakReference(callback)
        thread {
            weakReferenceCallback.get()?.getDetailsContact(listPerson[id])
        }
    }

    inner class ContactBinder : Binder() {

        fun getService() = this@BindContactService
    }

    interface IService {

        fun getService(): BindContactService?
    }
}