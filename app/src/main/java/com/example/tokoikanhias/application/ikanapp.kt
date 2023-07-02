package com.example.tokoikanhias.application

import android.app.Application
import com.example.tokoikanhias.repository.ikanRepository

class ikanapp: Application() {
    val database by lazy { ikanDatabase.getDatabase(this) }
    val repository by lazy { ikanRepository(database.ikanDao()) }
}