package com.example.composetutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ContactRepository

    init{
        val contactDao = ContactDataBase.getDatabase(application).contactDao()
        repository = ContactRepository(contactDao)
    }

    fun upsertContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertContact(contact)
        }
    }
    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(contact)
        }
    }
    fun getName(contactId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getName(contactId)
        }
    }
    fun getProfilePicture(contactId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getName(contactId)
        }
    }
}