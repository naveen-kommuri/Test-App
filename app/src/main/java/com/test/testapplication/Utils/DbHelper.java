package com.test.testapplication.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.testapplication.model.User;

/**
 * Created by Naveen on 8/19/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    String USERS_TB = "Users";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TaxNGo.db";
    private String FULLNAME_COL = "FULLNAME";
    private String EMAILID_COL = "EMAILID";
    private String CONTACTNO_COL = "CONTACTNO";
    private String PASSWORD_COL = "PASSWORD";
    private String PAN_COL = "PAN";
    private String GSTIN_COL = "GSTIN";
    private String GSTINREGDATE_COL = "GSTINREGDATE";
    private String REGTYPE_COL = "REGTYPE";
    private String PINCODE_COL = "PINCODE";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table " + USERS_TB + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FULLNAME TEXT NOT NULL,EMAILID TEXT NOT NULL,CONTACTNO TEXT NOT NULL,PASSWORD TEXT NOT NULL,PAN TEXT NOT NULL,GSTIN TEXT NOT NULL," +
                "GSTINREGDATE TEXT NOT NULL,REGTYPE TEXT NOT NULL,PINCODE TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUsers(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULLNAME_COL, user.getName());
        values.put(EMAILID_COL, user.getEmailId());
        values.put(CONTACTNO_COL, user.getContactNo());
        values.put(PASSWORD_COL, user.getPassword());
        values.put(PAN_COL, user.getPan());
        values.put(GSTIN_COL, user.getGstin());
        values.put(GSTINREGDATE_COL, user.getGstinRegDate());
        values.put(REGTYPE_COL, user.getRegType());
        values.put(PINCODE_COL, user.getPincode());
        long newRowId = db.insert(USERS_TB, null, values);
        return newRowId != -1;
    }

    public boolean isValidUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + USERS_TB + " where " + EMAILID_COL + " =? and " + PASSWORD_COL + " =?", new String[]{user.getEmailId(), user.getPassword()});
        return cursor.getCount() > 0;
    }
}
