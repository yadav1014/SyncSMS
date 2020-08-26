package com.shai.syncsms.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shai.syncsms.data.entity.Sms
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest : AppDatabaseTest() {
    private lateinit var smsDao: SmsDao
    val smsA = Sms(10.34.toString(), 1231432121, false)
    val smsB = Sms(0.34.toString(), 123143121, true)
    val smsC = Sms(11.34.toString(), 0, false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        smsDao = db.smsDao()

        runBlocking {
            smsDao.insertAll(listOf(smsA, smsB, smsC))
        }
    }

    @Test
    fun testGetAllSms() {
        runBlocking {
            val list = smsDao.getAllSms()

            Assert.assertThat(list.size, Matchers.equalTo(3))
            Assert.assertThat(list[0], Matchers.equalTo(smsC))
            Assert.assertThat(list[1], Matchers.equalTo(smsB))
            Assert.assertThat(list[2], Matchers.equalTo(smsA))
        }
    }

    @Test
    fun testGetUnSyncedSms() {
        runBlocking {
            val list = smsDao.getUnSyncedSms()

            Assert.assertThat(list.size, Matchers.equalTo(2))
            Assert.assertThat(list[0], Matchers.equalTo(smsA))
            Assert.assertThat(list[1], Matchers.equalTo(smsC))
        }
    }

    @Test
    fun testDeleteAll() {
        runBlocking {
            smsDao.deleteAll()
            val list = smsDao.getAllSms()
            Assert.assertThat(list.size, `is`(0))
        }
    }
}
