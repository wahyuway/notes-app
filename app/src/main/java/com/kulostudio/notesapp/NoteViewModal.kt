package com.kulostudio.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModal(application: Application) : AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    val repoository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNodesDao()
        repoository = NoteRepository(dao)
        allNotes = repoository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repoository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repoository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repoository.insert(note)
    }
}