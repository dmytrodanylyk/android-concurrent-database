package com.dd.database;

import android.database.sqlite.SQLiteDatabase;

public interface QueryExecutor {

    public void run(SQLiteDatabase database);
}
