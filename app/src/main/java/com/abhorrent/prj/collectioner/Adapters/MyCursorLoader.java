package com.abhorrent.prj.collectioner.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.abhorrent.prj.collectioner.DB.DB;

import java.util.concurrent.TimeUnit;

public class MyCursorLoader extends CursorLoader {

    DB db;

    public MyCursorLoader(Context context, DB db) {
        super(context);
        this.db = db;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = db.getAllData();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cursor;
    }

}
