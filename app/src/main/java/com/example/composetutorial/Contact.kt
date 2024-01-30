package com.example.composetutorial

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Here
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo val id: Int,
    @ColumnInfo val presentedName: String,
    @ColumnInfo val profilePicture: String
)
