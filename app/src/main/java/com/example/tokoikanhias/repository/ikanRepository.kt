package com.example.tokoikanhias.repository

import com.example.tokoikanhias.dao.ikanDao
import com.example.tokoikanhias.model.ikan
import kotlinx.coroutines.flow.Flow

class ikanRepository (private val ikanDao: ikanDao){
    val allikan: Flow<List<ikan>> = ikanDao.getAllikan()

    suspend fun  insertikan(ikan: ikan) {
        ikanDao.insertikan(ikan)
    }

    suspend fun  delete(ikan: ikan) {
        ikanDao.deleteikan(ikan)
    }

    suspend fun  update(ikan: ikan) {
        ikanDao.updateikan(ikan)
    }

}