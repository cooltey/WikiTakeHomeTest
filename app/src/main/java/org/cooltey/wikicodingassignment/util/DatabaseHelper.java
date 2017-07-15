package org.cooltey.wikicodingassignment.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database for Search History
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "database.db";
    private static final String TABLE = "search_history";

    private static final int DATABASEVERSION = 1;

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, DATABASEVERSION);
        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_CREATE_TABLE = "CREATE TABLE " + TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " keyword TEXT)";

        db.execSQL(DATABASE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public Cursor getKeywords() {
        return db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY _ID DESC", null);
    }


    public long insertKeyword(String value) {
        ContentValues args = new ContentValues();

        args.put("keyword", value);

        return db.insert(TABLE, null, args);
    }


    public int deleteKeyword(long rowId) {
        return db.delete(TABLE,
                "_ID = " + rowId,
                null
        );
    }

    public Cursor sql(String sqlString) {
        return db.rawQuery(sqlString, null);
    }
}