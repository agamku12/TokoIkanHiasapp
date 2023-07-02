package com.example.tokoikanhias.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tokoikanhias.dao.ikanDao
import com.example.tokoikanhias.model.Ikan

@Database (entities = [Ikan::class], version = 1, exportSchema = false)
abstract class ikanDatabase: RoomDatabase() {
    abstract fun ikanDao(): ikanDao

    companion object{
        private var INSTANCES: ikanDatabase? = null

        fun getDatabase(context: Context): ikanDatabase{
            return  INSTANCES ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ikanDatabase::class.java,
                    "ikan_database_1"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCES = instance
                instance
            }
        }
    }
}