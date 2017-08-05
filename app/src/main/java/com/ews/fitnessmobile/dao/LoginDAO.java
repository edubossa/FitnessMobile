package com.ews.fitnessmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ews.fitnessmobile.model.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wallace on 26/07/17.
 */
public class LoginDAO {

    private DBOpenHelper db;
    private static final String TABLE = "TB_LOGIN";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public LoginDAO(Context ctx) {
        this.db = new DBOpenHelper(ctx);
    }

    public String add(Login login) {
        SQLiteDatabase database = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, login.getUsername().toLowerCase());
        values.put(COLUMN_PASSWORD, login.getPassword());
        long result = database.insert(TABLE, null, values);
        database.close();
        return (result > 0) ? "Sucess" : "Error";
    }

    public List<Login> getAll() {
        List<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE;
        SQLiteDatabase database = this.db.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Login login = new Login(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                logins.add(login);
            } while (cursor.moveToNext());
        }
        return logins;
    }

    public Login getLogin(Login login) {
        SQLiteDatabase database = this.db.getReadableDatabase();
        String columns[] = {COLUMN_USERNAME, COLUMN_PASSWORD};
        String where = COLUMN_USERNAME + " = '" + login.getUsername().toLowerCase().trim()
            + "' and " + COLUMN_PASSWORD + " = '" + login.getPassword().trim() + "'";
        Cursor cursor = database.query(true, TABLE, columns, where, null, null, null, null, null);
        Login l = null;
        if (cursor.moveToFirst()) {
            l = new Login();
            l.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            l.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        }
        return l;
    }

}
