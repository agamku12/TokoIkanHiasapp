package com.example.tokoikanhias.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tokoikanhias.dao.ikanDao
import com.example.tokoikanhias.model.Ikan

@Database (entities = [Ikan::class], version = 2, exportSchema = false)
abstract class ikanDatabase: RoomDatabase() {
    abstract fun ikanDao(): ikanDao

    companion object{
        private var INSTANCES: ikanDatabase? = null

        //
        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ikan_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE ikan_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(context: Context): ikanDatabase{
            return  INSTANCES ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ikanDatabase::class.java,
                    "ikan_database_1"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCES = instance
                instance
            }
        }
    }
}