package com.abhorrent.prj.collectioner.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.abhorrent.prj.collectioner.R;

public class DB {

    private static final String DB_NAME = "Collections_DataBase";
    private static final String DB_COLLECTIONS = "Collection_List";
    private static final int DB_VERSION = 1;

    public static final String COLLECTION_ID = "_id";
    public static final String COLLECTION_TXT = "txt";
    public static final String COLLECTION_Description = "description";

    private static final String DB_CREATE =
            "create table " + DB_COLLECTIONS + "(" +
                    COLLECTION_ID + " integer primary key autoincrement, " +
                    //COLUMN_IMG + " integer, " +
                    COLLECTION_TXT + " text" +
                    COLLECTION_Description + " text" +
                    ");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    public Cursor getAllData(String tabName) {
        return mDB.query(tabName, null, null, null, null, null, null);
    }

    public void addRec(String tabName,String txt, int img) {
        ContentValues cv = new ContentValues();
        cv.put(COLLECTION_TXT, txt);
        //cv.put(COLUMN_IMG, img);
        mDB.insert(tabName, null, cv);
    }

    public void createNewTab(String query)
    {
        mDB.execSQL(query);
    }

    public void delRec(long id) {
        mDB.delete(DB_COLLECTIONS, COLLECTION_ID + " = " + id, null);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}