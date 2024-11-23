package br.unipar.minifinance.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MiniFinance.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS     = "users"
        private const val COLUMN_ID       = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL    = "email"
        private const val COLUMN_PASSWORD = "password"

        @Volatile
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return instance ?: synchronized(this) {
                instance ?: DatabaseHelper(context).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela de usuários
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(username: String, email: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = """
            SELECT email, password FROM $TABLE_USERS 
            WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?
        """
        val cursor = db.rawQuery(query, arrayOf(email, password))
        return db.rawQuery(query, arrayOf(email, password)).use { cursor ->
            cursor.count > 0
        }
    }
}
