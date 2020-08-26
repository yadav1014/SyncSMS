package com.shai.syncsms.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shai.syncsms.R
import com.shai.syncsms.databinding.ActivitySmsBinding
import com.shai.syncsms.ui.adapters.SmsListAdapter
import kotlinx.android.synthetic.main.activity_sms.*

class SmsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmsBinding
    private lateinit var viewModel: SmsViewModel
    private lateinit var smsListAdapter: SmsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeUi()
        checkReadSmsPermission()
    }

    companion object {
        private const val REQUEST_SMS_READ_PERMISSION = 1000
    }

    private fun initializeUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sms)
        viewModel = ViewModelProvider(this)[SmsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        smsListAdapter = SmsListAdapter()
        recyclerView.adapter = smsListAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager);

        btnAskPermission.setOnClickListener {
            checkReadSmsPermission()
        }
    }

    private fun checkReadSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions =
                    arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
                requestPermissions(
                    permissions,
                    REQUEST_SMS_READ_PERMISSION
                )
            } else {
                observeSmsData()
            }
        } else {
            observeSmsData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.setHasPermission(false)
        when (requestCode) {
            REQUEST_SMS_READ_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.setHasPermission(true)
                    observeSmsData()
                }
            }
        }
    }

    private fun observeSmsData() {
        viewModel.dbUpdated.observe(this, Observer {
            viewModel.getAllSms()
        })
        viewModel.smsList.observe(this, Observer {
            smsListAdapter.addUpdateItems(it)
        })
    }
}