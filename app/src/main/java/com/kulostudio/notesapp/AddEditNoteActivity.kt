package com.kulostudio.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdit: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addupdateBtn: Button
    lateinit var viewModal: NoteViewModal
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        noteTitleEdit = findViewById(R.id.idEditNoteTitle)
        noteDescriptionEdit = findViewById(R.id.idEditNoteDescription)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)
            addupdateBtn.setText("Update Note")
            noteTitleEdit.setText(noteTitle)
            noteDescriptionEdit.setText(noteDesc)
        } else {
            addupdateBtn.setText("Save Note")
        }

        addupdateBtn.setOnClickListener {
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteID
                    viewModal.updateNote(updateNote)
                    print("currentdate: "+currentDate)
                    Toast.makeText(applicationContext, "Note Updated...", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModal.addNote(Note(noteTitle, noteDescription,currentDate))
                    Toast.makeText(applicationContext, "Note Added...",Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }

        // Listen KeyEvent when edittext on focus, prevent edittext to loose focus when Enter/Done key is pressed
        noteDescriptionEdit.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                Log.d("enter key ", "presssed")
                return@OnKeyListener true
            }
            false
        })
    }
}
