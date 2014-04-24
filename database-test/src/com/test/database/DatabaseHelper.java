package com.test.database;


import com.test.database.dao.UserDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "sample_database";
    public static final int DATABASE_VERSION = 1;
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create all tables
        sqLiteDatabase.execSQL(UserDAO.getCreateTable(mContext));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // drop all tables
            sqLiteDatabase.execSQL(UserDAO.getDropTable(mContext));
            //re-create all tables
            onCreate(sqLiteDatabase);
        }
    }
}
