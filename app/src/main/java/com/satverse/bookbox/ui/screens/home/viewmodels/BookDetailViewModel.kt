package com.satverse.bookbox.ui.screens.home.viewmodels

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import com.satverse.bookbox.MainActivity
import com.satverse.bookbox.R
import com.satverse.bookbox.database.library.LibraryDao
import com.satverse.bookbox.database.library.LibraryItem
import com.satverse.bookbox.others.BookDownloader
import com.satverse.bookbox.repo.BookRepository
import com.satverse.bookbox.repo.models.Book
import com.satverse.bookbox.repo.models.BookSet
import com.satverse.bookbox.repo.models.ExtraInfo
import com.satverse.bookbox.utils.BookUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetailScreenState(
    val isLoading: Boolean = true,
    val bookSet: BookSet = BookSet(0, null, null, emptyList()),
    val extraInfo: ExtraInfo = ExtraInfo(),
    val bookLibraryItem: LibraryItem? = null,
    val error: String? = null
)

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    val libraryDao: LibraryDao,
    val bookDownloader: BookDownloader,
) : ViewModel() {
    var state by mutableStateOf(BookDetailScreenState())
    fun getBookDetails(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Reset Screen state.
            state = BookDetailScreenState()
            try {
                val bookSet = bookRepository.getBookById(bookId).getOrNull()!!
                val extraInfo = bookRepository.getExtraInfo(bookSet.books.first().title)
                state = if (extraInfo != null) {
                    state.copy(bookSet = bookSet, extraInfo = extraInfo)
                } else {
                    state.copy(bookSet = bookSet)
                }
                state = state.copy(
                    bookLibraryItem = libraryDao.getItemById(bookId.toInt()), isLoading = false
                )
            } catch (exc: Exception) {
                state =
                    state.copy(error = exc.localizedMessage ?: "unknown-error", isLoading = false)
            }
        }
    }

    @SuppressLint("Range")
    fun downloadBook(
        book: Book, activity: MainActivity, downloadProgressListener: (Float, Int) -> Unit
    ): String {
        return if (activity.checkStoragePermission()) {
            bookDownloader.downloadBook(book = book,
                downloadProgressListener = downloadProgressListener,
                onDownloadSuccess = {
                    insertIntoDB(book, bookDownloader.getFilenameForBook(book))
                    state = state.copy(bookLibraryItem = libraryDao.getItemById(book.id))
                })
            activity.getString(R.string.downloading_book)
        } else {
            activity.getString(R.string.storage_perm_error)
        }
    }

    private fun insertIntoDB(book: Book, filename: String) {
        val libraryItem = LibraryItem(
            bookId = book.id,
            title = book.title,
            authors = BookUtils.getAuthorsAsString(book.authors),
            filePath = "${BookDownloader.FILE_FOLDER_PATH}/$filename",
            createdAt = System.currentTimeMillis()
        )
        libraryDao.insert(libraryItem)
    }
}