/*
Copyright 2022 - 2023 Stɑrry Shivɑm

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.satverse.bookbox.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.satverse.bookbox.database.library.LibraryDao
import com.satverse.bookbox.database.library.LibraryItem
import com.satverse.bookbox.database.reader.ReaderDao
import com.satverse.bookbox.database.reader.ReaderItem
import com.satverse.bookbox.others.Constants

@Database(
    entities = [LibraryItem::class, ReaderItem::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class BookboxDatabase : RoomDatabase() {

    abstract fun getLibraryDao(): LibraryDao
    abstract fun getReaderDao(): ReaderDao

    companion object {

        @Volatile
        private var INSTANCE: BookboxDatabase? = null

        fun getInstance(context: Context): BookboxDatabase {
            /*
            if the INSTANCE is not null, then return it,
            if it is, then create the database and save
            in instance variable then return it.
            */
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookboxDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}