package com.soder.nexo.ui.viewmodel

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soder.nexo.data.model.FileItem
import com.soder.nexo.data.model.SortOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FileManagerViewModel : ViewModel() {

    private val rootDir: File = Environment.getExternalStorageDirectory()

    private val _currentDir = MutableStateFlow<File>(rootDir)
    val currentDir: StateFlow<File> = _currentDir.asStateFlow()

    private val _items = MutableStateFlow<List<FileItem>>(emptyList())
    val items: StateFlow<List<FileItem>> = _items.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _showHidden = MutableStateFlow(false)
    val showHidden: StateFlow<Boolean> = _showHidden.asStateFlow()

    private val _sortOption = MutableStateFlow(SortOption.NAME_ASC)
    val sortOption: StateFlow<SortOption> = _sortOption.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadDirectory(_currentDir.value)
    }

    fun loadDirectory(directory: File) {
        viewModelScope.launch {
            _isLoading.value = true
            _currentDir.value = directory

            val loadedItems = withContext(Dispatchers.IO) {
                try {
                    val rawFiles = directory.listFiles() ?: emptyArray()
                    rawFiles.map { file ->
                        FileItem(file = file)
                    }
                } catch (e: Exception) {
                    emptyList()
                }
            }

            _items.value = loadedItems
            _isLoading.value = false
        }
    }

    fun navigateTo(folder: File) {
        if (folder.isDirectory && folder.canRead()) {
            _searchQuery.value = ""
            loadDirectory(folder)
        }
    }

    fun navigateUp(): Boolean {
        val parent = _currentDir.value.parentFile
        if (parent != null && _currentDir.value.absolutePath != rootDir.absolutePath && parent.canRead()) {
            _searchQuery.value = ""
            loadDirectory(parent)
            return true
        }
        return false
    }

    fun navigateToRoot() {
        _searchQuery.value = ""
        loadDirectory(rootDir)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleShowHidden() {
        _showHidden.value = !_showHidden.value
    }

    fun setSortOption(option: SortOption) {
        _sortOption.value = option
    }

    fun refresh() {
        loadDirectory(_currentDir.value)
    }

    fun createFolder(name: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val newDir = File(_currentDir.value, name.trim())
            if (newDir.exists()) {
                withContext(Dispatchers.Main) { onError("Já existe um arquivo ou pasta com este nome.") }
                return@launch
            }
            if (newDir.mkdirs()) {
                withContext(Dispatchers.Main) {
                    refresh()
                    onSuccess()
                }
            } else {
                withContext(Dispatchers.Main) { onError("Erro ao criar a pasta.") }
            }
        }
    }

    fun renameItem(item: FileItem, newName: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val destination = File(item.file.parentFile, newName.trim())
            if (destination.exists()) {
                withContext(Dispatchers.Main) { onError("Já existe um item com esse nome.") }
                return@launch
            }
            if (item.file.renameTo(destination)) {
                withContext(Dispatchers.Main) {
                    refresh()
                    onSuccess()
                }
            } else {
                withContext(Dispatchers.Main) { onError("Não foi possível renomear o item.") }
            }
        }
    }

    fun deleteItem(item: FileItem, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = if (item.isDirectory) {
                item.file.deleteRecursively()
            } else {
                item.file.delete()
            }
            withContext(Dispatchers.Main) {
                if (success) {
                    refresh()
                    onSuccess()
                } else {
                    onError("Falha ao excluir o item.")
                }
            }
        }
    }

    fun getFilteredAndSortedItems(): List<FileItem> {
        val query = _searchQuery.value.trim().lowercase()
        val showHiddenVal = _showHidden.value
        val sort = _sortOption.value

        return _items.value
            .filter { item ->
                (showHiddenVal || !item.isHidden) &&
                        (query.isEmpty() || item.name.lowercase().contains(query))
            }
            .sortedWith { a, b ->
                // Pastas sempre no topo
                if (a.isDirectory && !b.isDirectory) return@sortedWith -1
                if (!a.isDirectory && b.isDirectory) return@sortedWith 1

                when (sort) {
                    SortOption.NAME_ASC -> a.name.compareTo(b.name, ignoreCase = true)
                    SortOption.NAME_DESC -> b.name.compareTo(a.name, ignoreCase = true)
                    SortOption.DATE_DESC -> b.lastModified.compareTo(a.lastModified)
                    SortOption.DATE_ASC -> a.lastModified.compareTo(b.lastModified)
                    SortOption.SIZE_DESC -> b.size.compareTo(a.size)
                    SortOption.SIZE_ASC -> a.size.compareTo(b.size)
                }
            }
    }
}
