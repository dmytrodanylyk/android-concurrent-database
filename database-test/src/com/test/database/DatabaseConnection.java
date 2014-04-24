package com.test.database;

import com.test.utils.L;

import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection {

    private int mOpenCounter;

    private static DatabaseConnection sInstance;
    private SQLiteOpenHelper mDatabaseHelper;
    private Database mDatabase;

    private DatabaseConnection(SQLiteOpenHelper helper) {
        mDatabaseHelper = helper;
    }

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (sInstance == null) {
            sInstance = new DatabaseConnection(helper);
        }
    }

    public static synchronized DatabaseConnection instance() {
        if (sInstance == null) {
            throw new IllegalStateException(DatabaseConnection.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return sInstance;
    }

    synchronized Database open() {
        if (mOpenCounter == 0) {
            // Opening new database
            mDatabase = new Database(mDatabaseHelper.getWritableDatabase());
        }
        mOpenCounter++;
        L.d("Database open counter: " + mOpenCounter);
        return mDatabase;
    }

    synchronized void close() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            // Closing database
            mDatabaseHelper.close();

        }
        L.d("Database open counter: " + mOpenCounter);
    }

}
