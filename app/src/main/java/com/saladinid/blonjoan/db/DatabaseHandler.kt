package `in`.eyehunt.sqliteandroidexample.db

import `in`.eyehunt.sqliteandroidexample.model.Users
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Eyehunt Team on 07/06/18.
 */
class DatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($ID Integer PRIMARY KEY, $FIRST_NAME TEXT, $LAST_NAME TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }

    //Inserting (Creating) data
    fun addUser(user: Users): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    //get all users
    fun getAllUsers(): String {
        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))

                    allUser = "$allUser\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    fun findUser(id:Int): String {
        var userFind: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME WHERE ID=$id "

        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))

                    userFind = "$userFind\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return userFind
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateUser(user: Users) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)

        // updating row
        db.update(TABLE_NAME, values, "$ID = ?",
                arrayOf(user.id.toString()))
        db.close()
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(user: Users) {
        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_NAME, "$ID = ?",
                arrayOf(user.id.toString()))
        db.close()
    }

    companion object {
        private val DB_NAME = "UsersDB"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "users"
        private val ID = "id"
        private val FIRST_NAME = "FirstName"
        private val LAST_NAME = "LastName"
    }
}