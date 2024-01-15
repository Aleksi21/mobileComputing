package com.example.composetutorial

class ContactRepository(private val contactDao: ContactDao) {
    suspend fun upsertContact(contact: Contact){
        contactDao.upsertContact(contact)
    }
    suspend fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }
    suspend fun getName(contactId: Long){
        contactDao.getName(contactId)
    }
    suspend fun getProfilePicture(contactId: Long){
        contactDao.getName(contactId)
    }
}