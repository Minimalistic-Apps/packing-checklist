package com.minimalisticapps.packingchecklist

import android.app.Activity
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ConfirmationDialog(
    onPositiveClick: (value: Boolean) -> Unit,
    onDismiss: (value: Boolean) -> Unit,
    text: String
) {
    val mContext = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = {
            onDismiss(false)
        },
        confirmButton = {
            TextButton(onClick = { onPositiveClick(false) })
            { Text(text = mContext.getString(R.string.yes)) }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss(false) })
            { Text(text = mContext.getString(R.string.no)) }
        },
        text = { Text(text = text) },
    )
}
