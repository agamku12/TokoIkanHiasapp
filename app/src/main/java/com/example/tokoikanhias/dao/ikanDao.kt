package com.example.tokoikanhias.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tokoikanhias.model.ikan
import kotlinx.coroutines.flow.Flow

@Dao
interface ikanDao {
    @Query("SELECT * FROM 'ikan_table' ORDER BY name ASC")
    fun getAllikan(): Flow<List<ikan>>

    @Insert
    suspend fun insertikan(ikan: ikan)

    @Delete
    suspend fun deleteikan(ikan: ikan)

    @Update
    suspend fun updateikan(ikan: ikan)
}
