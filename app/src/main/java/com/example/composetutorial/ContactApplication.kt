package com.example.composetutorial

import android.app.Application

class ContactApplication : Application() {
    val database by lazy { ContactDataBase.getDatabase(this) }
    val repository by lazy { ContactRepository(database.contactDao()) }
}