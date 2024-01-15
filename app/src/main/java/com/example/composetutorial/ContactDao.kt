package com.example.composetutorial

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT presentedName FROM contact_table WHERE id = :contactId")
    suspend fun getName(contactId: Long): String?

    @Query("SELECT profilePicture FROM contact_table WHERE id = :contactId")
    suspend fun getProfilePicture(contactId: Long): String?
}