package com.test.database.utils;

import android.database.Cursor;

public class CursorParser {

    private Cursor mCursor;
    private int mIndex;

    public CursorParser(Cursor cursor) {
        mCursor = cursor;
        mIndex = -1;
    }

    public long readLong() {
        mIndex++;
        return mCursor.getLong(mIndex);
    }

    public int readInt() {
        mIndex++;
        return mCursor.getInt(mIndex);
    }

    public double readDouble() {
        mIndex++;
        return mCursor.getDouble(mIndex);
    }

    public float readFloat() {
        mIndex++;
        return mCursor.getFloat(mIndex);
    }

    public String readString() {
        mIndex++;
        return mCursor.getString(mIndex);
    }

    public boolean readBoolean() {
        mIndex++;
        return mCursor.getInt(mIndex) != 0;
    }
}
