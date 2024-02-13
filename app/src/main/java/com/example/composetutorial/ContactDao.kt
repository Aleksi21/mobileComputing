package com.example.composetutorial

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert
    fun upsertContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getContacts(): List<Contact>

    @Query("SELECT * FROM contact LIMIT 1")
    fun getContact(): Contact?
}