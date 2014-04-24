package com.test.database;

import com.test.database.utils.DatabaseParams;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    private SQLiteDatabase mDatabase;

    public Database(SQLiteDatabase database) {
        mDatabase = database;
    }

    public int delete(DatabaseParams.Delete params) {
        return mDatabase.delete(params.table, params.whereClause, params.whereArgs);
    }

    public long insert(DatabaseParams.Insert params) {
        return mDatabase.insert(params.table, params.nullColumnHack, params.values);
    }

    public Cursor select(DatabaseParams.Select params) {
        return mDatabase.query(params.distinct, params.table, params.columns, params.selection,
                params.selectionArgs, params.groupBy, params.having, params.orderBy, params.limit);
    }

    public int update(SQLiteDatabase database, DatabaseParams.Update params) {
        return database.update(params.table, params.values, params.whereClause, params.whereArgs);
    }

    public void beginTransaction() {
        mDatabase.beginTransaction();
    }

    public void endTransaction() {
        mDatabase.endTransaction();
    }

    public void setTransactionSuccessful() {
        mDatabase.setTransactionSuccessful();
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDatabase.rawQuery(sql, selectionArgs);
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        mDatabase.execSQL(sql, bindArgs);
    }

    public void execSQL(String sql) throws SQLException {
        mDatabase.execSQL(sql);
    }
}
