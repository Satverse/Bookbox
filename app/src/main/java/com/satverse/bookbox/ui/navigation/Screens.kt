package com.satverse.bookbox.ui.navigation

const val BOOK_ID_ARG_KEY = "bookId"
const val CATEGORY_DETAIL_ARG_KEY = "category"
const val READER_CHAPTER_INDEX_KEY = "readerChapterIndex"

sealed class Screens(val route: String) {

    object BookDetailScreen : Screens("book_detail_screen/{$BOOK_ID_ARG_KEY}") {
        fun withBookId(id: String): String {
            return this.route.replace("{$BOOK_ID_ARG_KEY}", id)
        }
    }

    object CategoryDetailScreen : Screens("category_detail_screen/{$CATEGORY_DETAIL_ARG_KEY}") {
        fun withCategory(category: String): String {
            return this.route.replace("{$CATEGORY_DETAIL_ARG_KEY}", category)
        }
    }

    object ReaderDetailScreen : Screens("reader_detail_screen/{$BOOK_ID_ARG_KEY}") {
        fun withBookId(id: String): String {
            return this.route.replace("{$BOOK_ID_ARG_KEY}", id)
        }
    }

    object ReaderScreen : Screens("reader_screen/{$BOOK_ID_ARG_KEY}/{$READER_CHAPTER_INDEX_KEY}") {
        fun withBookId(id: String, idx: Int = -1): String {
            return this.route.replace("{$BOOK_ID_ARG_KEY}", id)
                .replace("{$READER_CHAPTER_INDEX_KEY}", idx.toString())
        }
    }

    object WelcomeScreen : Screens("welcome_screen")

    object OSLScreen : Screens("osl_screen")

    object AboutScreen : Screens("about_screen")

    object PrivacyPolicy : Screens("privacy_policy")
}
