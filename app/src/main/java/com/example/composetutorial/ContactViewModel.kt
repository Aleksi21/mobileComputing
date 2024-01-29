package com.example.composetutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ContactViewModel(private val repository: ContactRepository): ViewModel() {

    val getName : LiveData<List<Contact>> = repository.getName.asLiveData()
    val getProfilePicture : LiveData<List<Contact>> = repository.getProfilePicture.asLiveData()


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
//    suspend fun getName(contactId: Long): String? {
//        return repository.getName(contactId)
//    }
//    suspend fun getProfilePicture(contactId: Long): String? {
//            return repository.getProfilePicture(contactId)
//    }
}

class ContactViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}