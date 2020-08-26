package com.shai.syncsms.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shai.syncsms.data.entity.Sms
import com.shai.syncsms.data.repository.SmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class SmsViewModel : ViewModel(), KoinComponent {

    val smsRepository: SmsRepository by inject()
    var hasPermission = MutableLiveData<Boolean>().apply { postValue(true) }
    var smsList = MutableLiveData<ArrayList<Sms>>()
    var dbUpdated: MutableLiveData<Boolean> = SmsRepository.dbUpDated


    fun getAllSms() {
        CoroutineScope(Dispatchers.IO).launch {
            smsList.postValue(smsRepository.getAllSms())
        }
    }

    fun setHasPermission(value: Boolean) {
        hasPermission.value = value
    }
}