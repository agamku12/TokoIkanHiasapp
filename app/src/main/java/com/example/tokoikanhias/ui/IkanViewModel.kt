package com.example.tokoikanhias.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tokoikanhias.model.Ikan
import com.example.tokoikanhias.repository.ikanRepository
import kotlinx.coroutines.launch

class ikanViewModel (private val repository: ikanRepository): ViewModel() {
    val allikan: LiveData<List<Ikan>> = repository.allikan.asLiveData()
    fun insert(ikan: Ikan) = viewModelScope.launch {
        repository.insertikan(ikan)
    }

    fun delete(ikan: Ikan) = viewModelScope.launch {
        repository.delete(ikan)
    }

    fun update(ikan: Ikan) = viewModelScope.launch {
        repository.update(ikan)
    }
}

class ikanViewModelFactory(private val repository: ikanRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((ikanViewModel::class.java))){
            return ikanViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}