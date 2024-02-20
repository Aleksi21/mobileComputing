package com.example.composetutorial

import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import android.Manifest
import android.content.Intent
import java.io.FileOutputStream

@Composable
fun DetailScreen(navController: NavController, dataBase: ContactDataBase) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val file = File(context.filesDir, "picked_image.jpg")

    var name by remember { mutableStateOf("Lexi") }
    var contact by remember { mutableStateOf<Contact?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri

            selectedImageUri?.let { uris ->
                val inputStream = context.contentResolver.openInputStream(uris)
                inputStream?.use { input ->
                    val outputStream = FileOutputStream(file)
                    input.copyTo(outputStream)
                    outputStream.close()
                }
            }
        }
    )
    LaunchedEffect(key1 = dataBase){
        contact = withContext(Dispatchers.IO) {
            dataBase.contactDao().getContact()
        }
    }
    LaunchedEffect(key1 = contact){
        if (contact != null) {
            name = contact?.presentedName ?: ""
        }
    }

    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 30.dp)
    ){
        item{
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                OutlinedTextField(value = name, onValueChange = { text ->
                    name = text
                },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        contact = dataBase.contactDao().getContact()
                        if (contact != null) {
                            dataBase.contactDao().deleteContact(contact!!)
                        }
                        val newContact = Contact(
                            presentedName = name,
                        )
                        contact = newContact
                        contact?.let { dataBase.contactDao().upsertContact(it) }
                    }
                }) {
                    Text(text = "Add")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Left
            ){
                AsyncImage(
                    model = selectedImageUri?.toString() ?: Uri.fromFile(file).toString(),//painter = painterResource(R.drawable.cube),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }){
                    Text(text = "Pick a profile picture")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Left
            ){
                Button(onClick = {
                    val intent = Intent().apply {
                        action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        putExtra("app_package", context.packageName)
                        putExtra("app_uid", context.applicationInfo.uid)
                        putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                    }
                    context.startActivity(intent)
                }){
                    Text(text = "Enable notifications")
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.clickable {
                navController.popBackStack()
            },
            text = "Back",
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
@Preview(showBackground = true)
fun DetailScreenPreview(){
    //DetailScreen(navController = rememberNavController(), contactViewModel = contactViewModel)
}