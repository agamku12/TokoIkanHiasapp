package com.example.tokoikanhias.repository

import com.example.tokoikanhias.dao.ikanDao
import com.example.tokoikanhias.model.Ikan
import kotlinx.coroutines.flow.Flow

class ikanRepository (private val ikanDao: ikanDao){
    val allikan: Flow<List<Ikan>> = ikanDao.getAllikan()

    suspend fun  insertikan(ikan: Ikan) {
        ikanDao.insertikan(ikan)
    }

    suspend fun  delete(ikan: Ikan) {
        ikanDao.deleteikan(ikan)
    }

    suspend fun  update(ikan: Ikan) {
        ikanDao.updateikan(ikan)
    }

}