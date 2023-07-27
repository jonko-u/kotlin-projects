package com.jonkoit.notes

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

// In UserDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class, Note::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                )
                    .addMigrations(Migration1To2())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
class Migration1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Perform any database schema changes here to upgrade from version 1 to version 2
        // For example, if you are adding the 'user' and 'email' columns to the 'notes' table,
        // you can execute an SQL query to add the columns.

        // Example:
        database.execSQL("ALTER TABLE notes ADD COLUMN username TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE notes ADD COLUMN email TEXT NOT NULL DEFAULT ''")
    }
}