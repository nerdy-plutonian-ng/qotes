package com.plutoapps.qotes.ui.screens.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.repositories.BgQoteFetchRepo
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTab(modifier: Modifier = Modifier, reminderTime: Long?, setReminder: (Long?) -> Unit) {



    val context = LocalContext.current

    val timeState = rememberTimePickerState()

    var isReminderOn by rememberSaveable {
        mutableStateOf(false)
    }

    var openTimeDialog by rememberSaveable { mutableStateOf(false) }

    val dismissTimePicker = {
        openTimeDialog = false
    }

    val setAlarm = {
        openTimeDialog = false
        val timeNow = Date()
        timeNow.hours = timeState.hour
        timeNow.minutes = timeState.minute
        BgQoteFetchRepo(context,timeNow.time).fetchQote()
        setReminder(timeNow.time)
        Toast.makeText(context,"Your Qote will be delivered every day at ${timeState.hour} : ${timeState.minute}.",Toast.LENGTH_LONG).show()
    }

    fun showTime(){
        isReminderOn = true
        openTimeDialog = true
    }

    val requestPostNotificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ){
            result ->
        if(result){
            showTime()

        }
    }

    if(openTimeDialog){
        TimePickerDialogue(onConfirmation = setAlarm, onDismissRequest = dismissTimePicker,
            dialogTitle = "What time should we deliver your Qote to you every day?",
            icon = R.drawable.reminder,
            timeState = timeState)
    }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Settings", style = MaterialTheme.typography.titleLarge)
            ListItem(headlineContent = { Text("Reminders") },
                supportingContent = { Text(if(reminderTime == null) "Turned Off" else "You will be notified ${Date(reminderTime).hours}:${Date(reminderTime).minutes} everyday") },
                trailingContent = {
                    Switch(checked = reminderTime != null, onCheckedChange = { value ->
                        if(value){
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                                requestPostNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                showTime()
                            }
                        } else {
                            setReminder(null)
                        }
                    })
                })
        }

}

@Preview(showBackground = true)
@Composable
fun SettingsTabPreview() {
    SettingsTab(reminderTime = null, setReminder = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogue(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    icon: Int,
    timeState : TimePickerState
) {
    AlertDialog(
        icon = {
            Icon(painter = painterResource(id = icon), contentDescription = null)
        },
        title = {
            Text(text = dialogTitle, textAlign = TextAlign.Center)
        },
        text = {
            TimePicker(state = timeState)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}