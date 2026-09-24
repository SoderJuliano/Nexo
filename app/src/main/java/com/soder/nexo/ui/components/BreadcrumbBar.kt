package com.soder.nexo.ui.components

import android.os.Environment
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun BreadcrumbBar(
    currentDir: File,
    onNavigateTo: (File) -> Unit
) {
    val scrollState = rememberScrollState()
    val rootPath = Environment.getExternalStorageDirectory().absolutePath

    // Gera lista de diretórios ancestrais a partir da raiz de armazenamento
    val pathSegments = mutableListOf<File>()
    var temp: File? = currentDir

    while (temp != null && temp.absolutePath.startsWith(rootPath)) {
        pathSegments.add(0, temp)
        if (temp.absolutePath == rootPath) break
        temp = temp.parentFile
    }

    LaunchedEffect(currentDir) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onNavigateTo(Environment.getExternalStorageDirectory()) }) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Início",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        pathSegments.forEachIndexed { index, folder ->
            val isLast = index == pathSegments.size - 1
            val displayName = if (folder.absolutePath == rootPath) "Armazenamento" else folder.name

            TextButton(
                onClick = { onNavigateTo(folder) },
                enabled = !isLast
            ) {
                Text(
                    text = displayName,
                    color = if (isLast) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            if (!isLast) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}
