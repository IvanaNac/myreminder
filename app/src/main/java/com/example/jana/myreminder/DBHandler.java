package com.example.jana.myreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EVENTS.db";
    public static final String TABLE_NAME = "events";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_PHOTO = "photoSrc";
    public static final String COLUMN_COMMENT = "comment";


    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_QUERY =
            "CREATE TABLE " + TABLE_NAME + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                    " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_PHOTO +
                    " TEXT, " + COLUMN_COMMENT + " TEXT );";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addEvent(Events event, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.getmName());
        values.put(COLUMN_DATE, event.getDateTime());
        values.put(COLUMN_PHOTO, event.getmPhoto());
        values.put(COLUMN_COMMENT, event.getmComment());

        db.insert(TABLE_NAME, null, values);
        Log.e("DATABASE OPERATIONS", "One row is inserted..");
       // db.close();
    }

    public Cursor getEvent() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_NAME,COLUMN_DATE, COLUMN_PHOTO, COLUMN_COMMENT};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        return c;
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }
}
