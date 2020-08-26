package com.shai.syncsms.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shai.syncsms.data.entity.Sms

/**
 * The Data Access Object for the Sms class.
 */
@Dao
interface SmsDao {

    @Query("SELECT * FROM sms ORDER BY date ASC")
    suspend fun getAllSms(): Array<Sms>

    @Query("SELECT * FROM sms where synced = 0 ORDER BY date DESC")
    suspend fun getUnSyncedSms(): List<Sms>

    @Query("UPDATE sms SET synced = 1 WHERE uid IN (:ids)")
    suspend fun updateSyncStatus(ids: List<Long>)

//    @Query("SELECT * FROM sms WHERE uid IN (:ids) ORDER BY date ASC")
//    suspend fun getSmsByIds(ids: List<Long>): List<Sms>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sms: List<Sms>): List<Long>

    @Query("DELETE FROM sms")
    suspend fun deleteAll()
}