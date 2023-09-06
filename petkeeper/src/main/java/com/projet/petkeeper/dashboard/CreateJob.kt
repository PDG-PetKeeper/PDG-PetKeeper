package com.projet.petkeeper.dashboard

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projet.petkeeper.R
import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.PetType
import com.projet.petkeeper.ui.theme.PetkeeperTheme
import java.util.GregorianCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJob(
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
                .verticalScroll(rememberScrollState())
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
                })

            Spacer(modifier = Modifier.height(8.dp))


            var textName by remember { mutableStateOf(TextFieldValue("")) }
            // for preview add same text to all the fields


            //TODO
            // Add a the take a picture logic


            // Normal Text Input field with floating label
            // placeholder is same as hint in xml of edit text
            OutlinedTextField(
                value = textName,
                onValueChange = { newValue -> textName = newValue },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text("Title") },
                placeholder = { Text("placeholder") },
            )

            var text2 by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Text Input Field
            OutlinedTextField(
                value = text2,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Animal") },
                placeholder = { Text(text = "12334444") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    text2 = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            var text6 by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Text Input Field
            OutlinedTextField(
                value = text6,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Location") },
                placeholder = { Text(text = "Lausanne") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    text6 = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            var text3 by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = text3,
                leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Begin date") },
                placeholder = { Text(text = "11-12-2023") },
                onValueChange = {
                    text3 = it
                }
            )

            var text4 by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = text4,
                leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "End date") },
                placeholder = { Text(text = "17-12-2023") },
                onValueChange = {
                    text4 = it
                }
            )


            var textDescription by remember { mutableStateOf(TextFieldValue("")) }
            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon
            OutlinedTextField(
                value = textDescription,
                leadingIcon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Description") },
                placeholder = { Text(text = "I have an ....") },
                onValueChange = {
                    textDescription = it
                }
            )


            Button(
                //TODO
                // Add the logic to publish the advert
                modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                onClick = {
                    val jobData = JobData(
                        id = -1L,
                        poster = 2, // need userData
                        worker = null,
                        images = listOf(R.drawable.cat_1), // need images
                        title = textName.text,
                        pet = PetType.cat, // need PetType selection
                        description = textDescription.text,
                        GregorianCalendar(2023,9,21), // need DatePicker
                        GregorianCalendar(2023,9,27), // need DatePicker
                        "12" // need

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
            CreateJob(onBackClick = {}, onPublishClick = {})
        }
    }
}