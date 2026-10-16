package com.example.tugaspertemuan5

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BiodataLayout(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.black))
        ) {
            Image(
                painter = painterResource(id = R.drawable.sapidermen),
                contentDescription = stringResource(id = R.string.content_desc_bg),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .align(Alignment.TopCenter)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.70f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.corner_radius_large),
                        topEnd = dimensionResource(id = R.dimen.corner_radius_large)
                    ))
                    .background(colorResource(id = R.color.white))
            ) {
                FormContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormContent() {
    val context = LocalContext.current

    var namaDepan by remember { mutableStateOf("") }
    var namaBelakang by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    var checkAndroid by remember { mutableStateOf(false) }
    var checkUiUx by remember { mutableStateOf(false) }
    var checkWeb by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )

    var expandedExperience by remember { mutableStateOf(false) }
    val experienceOptions = stringArrayResource(id = R.array.experience_options)

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        tglLahir = formatter.format(Date(millis))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(id = R.string.btn_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(id = R.string.btn_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val isEmailValid = email.endsWith("@gmail.com")
    val isEmailError = email.isNotEmpty() && !isEmailValid

    // Mengecek apakah minimal ada 1 skill yang dicentang
    val isSkillSelected = checkAndroid || checkUiUx || checkWeb

    // Form valid JIKA semua terisi, email benar, DAN minimal 1 skill dicentang
    val isFormValid = namaDepan.isNotBlank() &&
            namaBelakang.isNotBlank() &&
            email.isNotBlank() &&
            isEmailValid &&
            tglLahir.isNotBlank() &&
            experience.isNotBlank() &&
            isSkillSelected

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_horizontal),
                vertical = dimensionResource(id = R.dimen.padding_vertical)
            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.form_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.purple_title)
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_small)))

        Text(
            text = stringResource(id = R.string.form_subtitle),
            fontSize = 12.sp,
            color = colorResource(id = R.color.gray_subtitle)
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_xlarge)))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_medium))
        ) {
            OutlinedTextField(
                value = namaDepan,
                onValueChange = { namaDepan = it },
                label = { Text(stringResource(id = R.string.first_name)) },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_small)),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = namaBelakang,
                onValueChange = { namaBelakang = it },
                label = { Text(stringResource(id = R.string.last_name)) },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_small)),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email)) },
            isError = isEmailError,
            supportingText = {
                if (isEmailError) {
                    Text(text = stringResource(id = R.string.error_email))
                }
            },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_small)),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_small)))


    }
}