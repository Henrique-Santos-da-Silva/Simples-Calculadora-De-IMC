package club.androidexpress.fitnesstracker.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE calc (id INTEGER PRIMARY KEY, type_calc TEXT, res DECIMAL, created_date DATETIME)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addItem(type: String?, response: Double?): Long {
        val db: SQLiteDatabase = writableDatabase
        var calcId: Long = 0L
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("pt", "BR"))
        val now: String = sdf.format(Date())

        try {
            db.beginTransaction()
            val values = ContentValues().apply {
                put("type_calc", type)
                put("res", response)
                put("created_date", now)
            }

            calcId = db.insertOrThrow("calc", null, values)
            db.setTransactionSuccessful()

        } catch (e: Exception) {
            Log.e("SQLite", e.message, e)

        } finally {
            if (db.isOpen) db.endTransaction()
        }

        return calcId
    }

    fun getRegisterBy(typeParam: String?): MutableList<Register> {
        var id: Int
        var type: String
        var response: Double
        var createdDate: String

        val registers = mutableListOf<Register>()
        val db: SQLiteDatabase = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM calc WHERE type_calc = ?", arrayOf(typeParam))

        try {
            if (cursor.moveToFirst()) {
                do {
                   id = cursor.getInt(cursor.getColumnIndex("id"))
                   type = cursor.getString(cursor.getColumnIndex("type_calc"))
                   response = cursor.getDouble(cursor.getColumnIndex("res"))
                   createdDate = cursor.getString(cursor.getColumnIndex("created_date"))

                    val register = Register(id, type, response, createdDate)

                    registers.add(register)

                } while (cursor.moveToNext())
            }

        } catch (e: Exception) {

        } finally {
            if (cursor != null && !cursor.isClosed) cursor.close()
        }

        return registers
    }

    fun removeItem(type: String, id: Int): Int {
        val db: SQLiteDatabase = writableDatabase
        db.beginTransaction()
        var calcId: Int = 0

        try {
            calcId = db.delete("calc", "id = ? and type_calc = ?", arrayOf(id.toString(), type))
            db.setTransactionSuccessful()

        } catch (e: Exception) {
            Log.e("SQLite", e.message, e)

        } finally {
            db.endTransaction()
        }

        return calcId
    }

    companion object {
        const val DB_NAME: String = "fitness_track.db"
        const val DB_VERSION: Int = 1
    }
}
