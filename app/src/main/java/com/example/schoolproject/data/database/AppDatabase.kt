package com.example.schoolproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TodoItemDbModel::class], version = 6, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        @Volatile
        private var db: AppDatabase? = null
        private const val DB_NAME = "todolist.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            db?.let { return it }
            synchronized(LOCK) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun todoDao(): TodoListDao
}