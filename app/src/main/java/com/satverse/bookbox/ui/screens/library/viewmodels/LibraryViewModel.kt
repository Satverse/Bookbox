package com.satverse.bookbox.ui.screens.library.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satverse.bookbox.database.library.LibraryDao
import com.satverse.bookbox.database.library.LibraryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(private val libraryDao: LibraryDao) : ViewModel() {
    val allItems: LiveData<List<LibraryItem>> = libraryDao.getAllItems()

    fun deleteItem(item: LibraryItem) {
        viewModelScope.launch(Dispatchers.IO) { libraryDao.delete(item) }
    }
}