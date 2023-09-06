package com.projet.petkeeper.dashboard

import android.net.Uri
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.projet.petkeeper.R
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.PetType
import com.projet.petkeeper.ui.PetKeeperUIState
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import java.util.GregorianCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJob(
    uiState: PetKeeperUIState,
    onBackClick: () -> Unit,
    onPublishClick: (JobData) -> Unit
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


            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp)
            ) {
                Text(text = "Add a pet !")
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
                placeholder = { Text("Advert title") },
            )

            var animal by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Text Input Field
            OutlinedTextField(
                value = animal,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Animal") },
                placeholder = { Text(text = "12334444") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    animal = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    location = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            var beginDate by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = beginDate,
                leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Begin date") },
                placeholder = { Text(text = "11-12-2023") },
                onValueChange = {
                    beginDate = it
                }
            )

            var endDate by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = endDate,
                leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "End date") },
                placeholder = { Text(text = "17-12-2023") },
                onValueChange = {
                    endDate = it
                }
            )

            var price by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = price,
                leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
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
                //TODO
                // Add the logic to publish the advert
                modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                onClick = {

                    // Upload image, retrieve url, upload advert, go back to job lis
                    val jobData = JobData(
                        id = -1L,
                        poster = 2, // need userData
                        worker = null,
                        image = R.drawable.cat_1, // need images
                        title = title.text,
                        pet = PetType.cat, // need PetType selection
                        description = description.text,
                        GregorianCalendar(2023,9,21), // need DatePicker
                        GregorianCalendar(2023,9,27), // need DatePicker
                        hourlyPay = price.text // need

                    )
                    onPublishClick(jobData)
                }
            ) {
                Text(" Publish ")
            }
        }
    }
}

@InternalTextApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewJobCreation() {
    PetkeeperTheme {
        Column {
            CreateJob(uiState = PetKeeperUIState(), onBackClick = {}, onPublishClick = {})
        }
    }
}