package com.projet.petkeeper.dashboard

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.projet.petkeeper.R
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import com.projet.petkeeper.utils.Constants.TAG
import com.projet.petkeeper.utils.convertMillisToDate
import java.util.*

/**
 * The screen to create a job advert.
 * @ param uiState: the state of the application
 * @ param onBackClick: the action to perform when the back button is clicked
 *                      -> goes back to the dashboard
 * @ param onPublishClick: the action to perform when the publish button is clicked
 *                      -> for now goes back to the dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJob(
    uiState: PetKeeperUIState,
    onBackClick: () -> Unit,
    //crashes when called from viewmodel on upload fonction
    onPublishClick: (JobData) -> Unit,
    userData: UserData?
) {
    BackHandler {
        onBackClick()
    }

    PetkeeperTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBar(
                title = {
                    Text(text = "Create an advert")
                },
                navigationIcon = {
                    // go back
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back",
                            )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))


            var selectImage by remember { mutableStateOf<Uri?>(null) }

            val galleryLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                    selectImage = it
                }


            Box {
                selectImage?.let { imageUri ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .size(100.dp)
                            .clickable { galleryLauncher.launch("image/*") }
                    )
                }
            }

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp)
            ) {
                Text(text = "Add a pet !")
            }

            var title by remember { mutableStateOf(TextFieldValue("")) }
            // for preview add same text to all the fields

            // Normal Text Input field with floating label
            // placeholder is same as hint in xml of edit text
            OutlinedTextField(
                value = title,
                onValueChange = { newValue -> title = newValue },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text("Title") },
                placeholder = { Text("Enter a title") },
            )

            var animal by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Text Input Field
            OutlinedTextField(
                value = animal,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Animal") },
                placeholder = { Text(text = "Cat or Dog") },
                onValueChange = {
                    animal = it
                }
            )

            var location by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Text Input Field
            OutlinedTextField(
                value = location,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Location") },
                placeholder = { Text(text = "Lausanne") },
                onValueChange = {
                    location = it
                }
            )

            val today = Date().time

            var startDate by remember {
                mutableStateOf(today)
            }

            var showStartDatePicker by remember {
                mutableStateOf(false)
            }

            ExposedDropdownMenuBox(
                expanded = showStartDatePicker,
                onExpandedChange = {showStartDatePicker = !showStartDatePicker}
            ) {
                OutlinedTextField(
                    value = convertMillisToDate(startDate),
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                    },
                    readOnly = true,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = { Text(text = "Begin date") },
                    onValueChange = {},
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                )

                if (showStartDatePicker) {
                    JobDatePickerDialog(
                        onDateSelected = { startDate = it },
                        onDismiss = { showStartDatePicker = false },
                        minDate = today,
                        pickedDate = startDate
                    )
                }
            }

            var endDate by remember {
                mutableStateOf(startDate)
            }

            var showEndDatePicker by remember {
                mutableStateOf(false)
            }

            ExposedDropdownMenuBox(
                expanded = showEndDatePicker,
                onExpandedChange = {showEndDatePicker = !showEndDatePicker}
            ) {
                OutlinedTextField(
                    value = convertMillisToDate(endDate),
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                    },
                    readOnly = true,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = { Text(text = "Begin date") },
                    onValueChange = {},
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                )

                if (showEndDatePicker) {
                    JobDatePickerDialog(
                        onDateSelected = { endDate = it },
                        onDismiss = { showEndDatePicker = false },
                        minDate = startDate,
                        pickedDate = endDate
                    )
                }
            }

            var price by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = price,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_attach_money_24),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "CHF") },
                placeholder = { Text(text = "20./H") },
                onValueChange = {
                    price = it
                }
            )


            var description by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = description,
                leadingIcon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Description") },
                placeholder = { Text(text = "I have an ....") },
                onValueChange = {
                    description = it
                }
            )


            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {

                    // Upload image, retrieve url, upload advert, go back to job lis
                    val jobData = JobData(
                        id = GregorianCalendar().timeInMillis.toString(),
                        poster = userData?.userId, // need userData
                        worker = null,
                        image = "", // need images
                        title = title.text,
                        pet = animal.text, // need PetType selection
                        description = description.text,
                        startDate = Timestamp(Date(startDate)),
                        endDate = Timestamp(Date(endDate)),
                        pay = price.text,
                        location = location.text
                    )

                    onPublishClick(jobData)
                    // this function can't be called from the viewmodel
                    // causes to crash the app. So it's here for now
                    uploadAll(selectImage, jobData)
                }
            ) {
                Text(" Publish ")
            }
        }
    }
}

/**
 * Uploads the image to firebase storage, then uploads the data to firestore
 * @param image: the image to upload
 * @param data: the data to upload
 */
fun uploadAll(image: Uri?, data: JobData){

    //function is using listeners
    var storageRef = FirebaseStorage.getInstance().reference.child("image")

    // Create a reference to 'images/random' on firebase storage
    storageRef = storageRef.child(UUID.randomUUID().toString())

    image?.let {
        // upload the image to firebase storage
        storageRef.putFile(it).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // once completed, upload the map to firestore with the download url and data
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    data.image = uri.toString()

                    // ref to storage
                    val firebaseFirestore = FirebaseFirestore.getInstance()
                    // uploading the map to firestore with its content
                    firebaseFirestore
                        .collection("jobs")
                        .document(data.id!!)
                        .set(data)
                        .addOnCompleteListener { firestoreTask ->

                        if (firestoreTask.isSuccessful) {
                            Log.d(TAG, "advert post added")

                        } else {
                            Log.d(TAG, "advert post failed")

                        }

                    }
                }
            } else {
                Log.d(TAG, "image upload failed")
            }
        }
    }
}

/**
 * A composable function to display a date picker dialog.
 * @param onDateSelected A callback function to handle a date selection.
 * @param onDismiss A callback function to handle a date picker dismiss.
 * @param minDate The minimum selectable date.
 * @param pickedDate The date to be selected by default.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDatePickerDialog(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    minDate: Long,
    pickedDate : Long
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = pickedDate,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= minDate
            }
        }
    )


    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                    onDismiss()
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@InternalTextApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewJobCreation() {
    PetkeeperTheme {
        Column {
            CreateJob(onBackClick = {}, onPublishClick = {}, userData = UserData(), uiState = PetKeeperUIState())
        }
    }
}