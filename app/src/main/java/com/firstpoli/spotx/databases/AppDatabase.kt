package com.firstpoli.spotx.databases

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.firstpoli.spotx.App
import com.firstpoli.spotx.models.BpData

@Database(entities = [BpData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DB = "clock.db"

        const val T_BpData = "t_alarm"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(AppDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(App.INSTANCE, AppDatabase::class.java, DB)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                            }
                        })
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun alarmDao(): BpDataDao

}
