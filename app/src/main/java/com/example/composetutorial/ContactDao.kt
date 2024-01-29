package com.example.composetutorial

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact_table WHERE id == :contactId")
    fun getName(contactId: Long): Flow<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE id == :contactId")
    fun getProfilePicture(contactId: Long): Flow<List<Contact>>
}