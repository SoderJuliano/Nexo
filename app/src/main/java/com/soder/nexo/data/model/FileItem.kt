package com.soder.nexo.data.model

import com.soder.nexo.util.FileUtils
import java.io.File

data class FileItem(
    val file: File,
    val name: String = file.name,
    val path: String = file.absolutePath,
    val isDirectory: Boolean = file.isDirectory,
    val size: Long = if (file.isDirectory) 0L else file.length(),
    val lastModified: Long = file.lastModified(),
    val isHidden: Boolean = file.isHidden || file.name.startsWith("."),
    val extension: String = if (file.isDirectory) "" else file.extension.lowercase(),
    val itemCount: Int = if (file.isDirectory) (file.list()?.size ?: 0) else 0
) {
    val formattedSize: String
        get() = if (isDirectory) "$itemCount itens" else FileUtils.formatSize(size)

    val formattedDate: String
        get() = FileUtils.formatDate(lastModified)
}

enum class SortOption {
    NAME_ASC,
    NAME_DESC,
    DATE_DESC,
    DATE_ASC,
    SIZE_DESC,
    SIZE_ASC
}
