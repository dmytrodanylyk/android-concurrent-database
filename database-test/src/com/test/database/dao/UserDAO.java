package com.test.database.dao;


import android.content.Context;
import android.database.Cursor;
import com.dd.database.R;
import com.test.User;
import com.test.database.Database;
import com.test.database.utils.ArrayUtils;
import com.test.database.utils.CursorParser;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Database mDatabase;
    private Context mContext;

    public UserDAO(Database database, Context context) {
        mDatabase = database;
        mContext = context;
    }

    public static String getCreateTable(Context context) {
        return context.getString(R.string.create_table_user);
    }

    public static String getDropTable(Context context) {
        return context.getString(R.string.drop_table_users);
    }

    public void deleteAll() {
        mDatabase.execSQL(mContext.getString(R.string.delete_all_users));
    }

    public void insert(List<User> userList) {
        String sql = mContext.getString(R.string.insert_user);
        String[] bindArgs;
        for (User user : userList) {
            bindArgs = ArrayUtils.build(user.getName(), user.getAge());
            mDatabase.execSQL(sql, bindArgs);
        }
    }

    public void insert(User user) {
        String[] bindArgs = ArrayUtils.build(user.getName(), user.getAge());
        mDatabase.execSQL(mContext.getString(R.string.insert_user), bindArgs);
    }

    public void updateNameByAge(String name, int age) {
        String[] bindArgs = ArrayUtils.build(name, age);
        mDatabase.execSQL(mContext.getString(R.string.update_user_name_by_age), bindArgs);
    }

    public List<User> selectByAge(int age) {
        String[] selectionArgs = ArrayUtils.build(age);
        String query = mContext.getString(R.string.select_users_by_age);
        Cursor cursor = mDatabase.rawQuery(query, selectionArgs);

        List<User> dataList = manageCursor(cursor);

        closeCursor(cursor);

        return dataList;
    }

    public List<User> selectAll() {
        Cursor cursor = mDatabase.rawQuery(mContext.getString(R.string.select_all_users), null);
        List<User> dataList = manageCursor(cursor);
        closeCursor(cursor);

        return dataList;
    }

    protected User cursorToData(Cursor cursor) {
        CursorParser parser = new CursorParser(cursor);

        User user = new User();
        user.setId(parser.readLong());
        user.setAge(parser.readInt());
        user.setName(parser.readString());

        return user;
    }

    protected void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    protected List<User> manageCursor(Cursor cursor) {
        List<User> dataList = new ArrayList<User>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToData(cursor);
                dataList.add(user);
                cursor.moveToNext();
            }
        }
        return dataList;
    }
}
