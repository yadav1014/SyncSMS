package com.shai.syncsms.data.repository

import androidx.lifecycle.MutableLiveData
import com.shai.syncsms.data.db.AppDatabase
import com.shai.syncsms.data.entity.Sms
import com.shai.syncsms.data.network.SmsApi
import com.shai.syncsms.worker.SyncSmsWorkerHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent


class SmsRepository constructor(private val smsApi: SmsApi, private val db: AppDatabase) :
    KoinComponent {
    companion object {
        val dbUpDated = MutableLiveData(false)
    }

    fun saveSmsList(smsList: List<Sms>) {
        CoroutineScope(IO).launch {
            val smsDao = db.smsDao()
            smsDao.insertAll(smsList)
            dbUpDated.postValue(true)
            SyncSmsWorkerHelper.runSingleWorker()
        }
    }

    suspend fun getAllSms(): ArrayList<Sms> {
        val smsList = ArrayList<Sms>()
        val smsDao = db.smsDao()
        smsList.addAll(smsDao.getAllSms())
        return smsList
    }

    fun syncSms() {
        CoroutineScope(IO).launch {
            val smsDao = db.smsDao()
            val unSyncedSms = smsDao.getUnSyncedSms()

            //TODO: At this point we would make the api call
            //After successfully making the call updating status as synced in local DB
            //val pushSmsList = smsApi.pushSmsList(unSyncedSms)            //Call to push the data to server
            val idList = ArrayList<Long>()

            for (sms: Sms in unSyncedSms) {
                idList.add(sms.uid)
            }
            smsDao.updateSyncStatus(ids = idList)
            dbUpDated.postValue(true)
        }
    }
}