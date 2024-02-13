package com.example.composetutorial

import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    //val getName: Flow<List<Contact>> = contactDao.getName(0)
    //val getProfilePicture: Flow<List<Contact>> = contactDao.getProfilePicture(0)

    suspend fun upsertContact(contact: Contact){
        //contactDao.insertContact(contact)
    }
    suspend fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }
    //suspend fun getName(contactId: Long): String? {
        //    return contactDao.getName(contactId)
        //}
    //suspend fun getProfilePicture(contactId: Long): String? {
        //    return contactDao.getProfilePicture(contactId)
    //}
}
