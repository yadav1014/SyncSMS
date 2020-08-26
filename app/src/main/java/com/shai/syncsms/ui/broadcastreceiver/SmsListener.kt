package com.shai.syncsms.ui.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import com.shai.syncsms.data.entity.Sms
import com.shai.syncsms.data.repository.SmsRepository
import com.shai.syncsms.util.extractDoubles
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import kotlin.collections.ArrayList


class SmsListener : BroadcastReceiver(), KoinComponent {
    val smsRepository: SmsRepository by inject()
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == null) {
            return
        }
        if (intent.action != (Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            return
        }
        val smsMessages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Telephony.Sms.Intents.getMessagesFromIntent(intent)
        } else {
            extractSms(intent)
        }
        saveSmsToDb(smsMessages)
    }


    @Suppress("DEPRECATION")
    private fun extractSms(intent: Intent): Array<SmsMessage> {
        val pdus = intent.getExtras()?.get("pdus") as Array<*>
        val list = ArrayList<SmsMessage>()
        for (message in pdus) {
            list.plus(SmsMessage.createFromPdu(message as ByteArray))
        }
        return list.toArray() as Array<SmsMessage>
    }

    private fun saveSmsToDb(smsMessages: Array<SmsMessage>) {
        val smsList = ArrayList<Sms>()
        for (message in smsMessages) {
            val sms = Sms(message.messageBody.extractDoubles(), Date().time, false)
            if (sms.amount.isNotEmpty()) {
                smsList.add(sms)
            }
        }
        smsRepository.saveSmsList(smsList)
    }
}