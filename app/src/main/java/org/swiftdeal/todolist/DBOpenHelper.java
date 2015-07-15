package org.swiftdeal.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Faizan Ayubi on 15-07-2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper{

    //Constants for db name and version
    private static final String DATABASE_NAME = "todos.db";
    private static final int DATABASE_VERSION = 1;

    //constants for identifying table and columns
    public static final String TABLE_TODOS = "todos";
    public static final String TODO_ID = "_id";
    public static final String TODO_TEXT = "todoText";
    public static final String TODO_CREATED = "todoCreated";

    public static final String[] ALL_COLUMNS = {TODO_ID, TODO_TEXT, TODO_CREATED};

    //SQL to create table
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TODOS + " ( " +
                    TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TODO_TEXT + " TEXT, " +
                    TODO_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        onCreate(db);
    }
}
