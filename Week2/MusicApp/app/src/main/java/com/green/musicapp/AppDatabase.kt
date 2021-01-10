package com.green.musicapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [User::class, ItemSongOfChart::class], version = 1, exportSchema = false)
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao():UserDao

    // 어디서든 접근가능하고, 중복생성되지 않게 싱글톤
    companion object {
        private var instance:AppDatabase? = null

        // synchronized : 중복방지
        @Synchronized
        fun getInstance(context: Context) : AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database-app"
                )
                .allowMainThreadQueries()
                .build()
            }
            return instance
        }
    }
}
