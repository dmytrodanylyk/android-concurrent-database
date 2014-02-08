package com.dd.database.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtils {

    public int delete(SQLiteDatabase database, DatabaseParams.Delete params) {
        return database.delete(params.table, params.whereClause, params.whereArgs);
    }

    public long insert(SQLiteDatabase database, DatabaseParams.Insert params) {
        return database.insert(params.table, params.nullColumnHack, params.values);
    }

    public Cursor select(SQLiteDatabase database, DatabaseParams.Select params) {
        return database.query(params.distinct, params.table, params.columns, params.selection,
                params.selectionArgs, params.groupBy, params.having, params.orderBy, params.limit);
    }

    public int update(SQLiteDatabase database, DatabaseParams.Update params) {
        return database.update(params.table, params.values, params.whereClause, params.whereArgs);
    }

    public Cursor rawQuery(SQLiteDatabase database, String sql, String[] selectionArgs) {
        return database.rawQuery(sql, selectionArgs);
    }
}
