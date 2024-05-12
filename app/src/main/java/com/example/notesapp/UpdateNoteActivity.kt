package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityAddBinding
import com.example.notesapp.databinding.ActivityUpdateNoteBinding


class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDBHelper
    private var noteID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = NotesDBHelper(this)

        noteID = intent.getIntExtra("note_id", -1)
        if (noteID == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteID)
        binding.UpdateTitleEditText.setText(note.title)
        binding.UpdateContentEditText.setText(note.content)

        binding.UpdateSaveButton.setOnClickListener {
            val newTitle = binding.UpdateTitleEditText.text.toString()
            val newContent = binding.UpdateContentEditText.text.toString()
            val updatedNote = Note(noteID, newTitle, newContent)
            db.updateNOte(updatedNote)
            finish()
            Toast.makeText(this, "changes saved", Toast.LENGTH_SHORT).show()
        }

    }
}