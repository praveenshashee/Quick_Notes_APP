package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDBHelper(context: Context) : SQLiteOpenHelper(context, DataBase_Name, null, DataBase_Version) {

    companion object{
        private const val DataBase_Name = "notesapp.db"
        private const val DataBase_Version = 1
        private const val Table_Name = "allnotes"
        private const val Column_ID = "id"
        private const val Column_Title = "title"
        private const val Column_Content = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $Table_Name ($Column_ID INTEGER PRIMARY KEY, $Column_Title TEXT, $Column_Content TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $Table_Name"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Column_Title, note.title)
            put(Column_Content, note.content)
        }
        db.insert(Table_Name, null, values)
    }

    fun getAllNotes(): List<Note>{
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $Table_Name"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id  = cursor.getInt(cursor.getColumnIndexOrThrow(Column_ID))
            val title  = cursor.getString(cursor.getColumnIndexOrThrow(Column_Title))
            val content  = cursor.getString(cursor.getColumnIndexOrThrow(Column_Content))

            val note = Note(id, title, content)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNOte(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Column_Title, note.title)
            put(Column_Content, note.content)
        }
        val whereClause = "$Column_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(Table_Name, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID(noteID: Int): Note{
        val db = readableDatabase
        val query = "SELECT * FROM $Table_Name WHERE $Column_ID = $noteID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Column_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(Column_Title))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(Column_Content))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

    fun deleteNote(noteID: Int){
        val db = writableDatabase
        val whereClause = "$Column_ID = ?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(Table_Name, whereClause, whereArgs)
        db.close()
    }
}