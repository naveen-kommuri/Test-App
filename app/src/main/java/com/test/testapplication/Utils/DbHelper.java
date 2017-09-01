package com.test.testapplication.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.testapplication.model.Invoice;
import com.test.testapplication.model.Status;
import com.test.testapplication.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Naveen on 8/19/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    String USERS_TB = "Users";
    String INVOICES_TB = "Invoices";
    String INVOICE_TRANS_TB = "Transactions";
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
        sqLiteDatabase.execSQL("Create table " + INVOICES_TB + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MERCHANTNAME TEXT NOT NULL,INVOICENO TEXT NOT NULL,INVOICEAMOUNT TEXT NOT NULL,INVOICEGSTIN TEXT NOT NULL,INVOICEDATE TEXT NOT NULL,INVOICELOC TEXT NOT NULL,STATUS TEXT NOT NULL,UPDATEDTIME TEXT NOT NULL);");
        sqLiteDatabase.execSQL("Create table " + INVOICE_TRANS_TB + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "INVOICEID INTEGER NOT NULL,STATUS TEXT NOT NULL,UPDATEDTIME TEXT NOT NULL);");
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

    public boolean insertInvoice(Invoice invoice) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MERCHANTNAME", invoice.getMerchantName());
        values.put("INVOICEAMOUNT", invoice.getInvoiceAmount());
        values.put("INVOICEDATE", invoice.getInvoiceDate());
        values.put("INVOICELOC", invoice.getInvoiceFileLoc());
        values.put("INVOICEGSTIN", invoice.getInvoiceGSTIn());
        values.put("INVOICENO", invoice.getInvoiceNo());
        values.put("STATUS", Status.REGISTERED.toString());
        values.put("UPDATEDTIME", CommonUtil.getCurrentTime() + "");
        long rowId = db.insert(INVOICES_TB, null, values);
        return rowId != -1 ? insertTransaction(rowId, Status.REGISTERED) : false;
    }

    private boolean insertTransaction(long invoiceId, Status status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("INVOICEID", invoiceId);
        values.put("STATUS", status.toString());
        values.put("UPDATEDTIME", CommonUtil.getCurrentTime() + "");
        long rowId = db.insert(INVOICE_TRANS_TB, null, values);
        return rowId != -1;
    }


    public ArrayList<Invoice> getInvoices(String searchStr, String status) {
        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery("select i.* from " + INVOICES_TB + "i INNER JOIN " + INVOICE_TRANS_TB + "it  ON i.ID=it.INVOICEID", null);
        String appendQuery = (searchStr == null || searchStr.trim().length() == 0 ? "" : " where MERCHANTNAME like '%" + searchStr + "%'");
        String statusQuery = (status == null || status.trim().length() == 0 || status.equals(Status.ALL.toString()) ? "" : " where status = '" + status + "'");
        String query = "select * from " + INVOICES_TB + appendQuery + statusQuery;
        Log.e("Query", query);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Invoice> invoices = new ArrayList<>();
        while (cursor.moveToNext()) {
            invoices.add(new Invoice(cursor.getString(cursor.getColumnIndex("INVOICEAMOUNT")), cursor.getString(cursor.getColumnIndex("INVOICENO")), cursor.getString(cursor.getColumnIndex("INVOICEGSTIN"))
                    , cursor.getString(cursor.getColumnIndex("INVOICEDATE")), cursor.getString(cursor.getColumnIndex("MERCHANTNAME")), cursor.getString(cursor.getColumnIndex("INVOICELOC")))
                    .setInvoiceId(cursor.getInt(cursor.getColumnIndex("ID"))).setInvoiceStatus(cursor.getString(cursor.getColumnIndex("STATUS"))).setUpdatedTime(cursor.getString(cursor.getColumnIndex("UPDATEDTIME"))));
        }
        return invoices;
    }

    public Invoice getInvoiceBasedId(int invoiceId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + INVOICES_TB + " where ID=?", new String[]{String.valueOf(invoiceId)});
        while (cursor.moveToNext()) {
            return (new Invoice(cursor.getString(cursor.getColumnIndex("INVOICEAMOUNT")), cursor.getString(cursor.getColumnIndex("INVOICENO")), cursor.getString(cursor.getColumnIndex("INVOICEGSTIN"))
                    , cursor.getString(cursor.getColumnIndex("INVOICEDATE")), cursor.getString(cursor.getColumnIndex("MERCHANTNAME")), cursor.getString(cursor.getColumnIndex("INVOICELOC")))
                    .setInvoiceId(cursor.getInt(cursor.getColumnIndex("ID"))).setInvoiceStatus(cursor.getString(cursor.getColumnIndex("STATUS"))).setUpdatedTime(cursor.getString(cursor.getColumnIndex("UPDATEDTIME"))));
        }
        return null;
    }

    public ArrayList<Invoice> getInvoicesBasedStatus(String searchStr) {
        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery("select i.* from " + INVOICES_TB + "i INNER JOIN " + INVOICE_TRANS_TB + "it  ON i.ID=it.INVOICEID", null);
        String appendQuery = (searchStr == null || searchStr.trim().length() == 0 ? "" : " where MERCHANTNAME like '%" + searchStr + "%'");
        Cursor cursor = db.rawQuery("select * from " + INVOICES_TB + appendQuery, null);
        ArrayList<Invoice> invoices = new ArrayList<>();
        while (cursor.moveToNext()) {
            invoices.add(new Invoice(cursor.getString(cursor.getColumnIndex("INVOICEAMOUNT")), cursor.getString(cursor.getColumnIndex("INVOICENO")), cursor.getString(cursor.getColumnIndex("INVOICEGSTIN"))
                    , cursor.getString(cursor.getColumnIndex("INVOICEDATE")), cursor.getString(cursor.getColumnIndex("MERCHANTNAME")), cursor.getString(cursor.getColumnIndex("INVOICELOC")))
                    .setInvoiceId(cursor.getInt(cursor.getColumnIndex("ID"))).setInvoiceStatus(cursor.getString(cursor.getColumnIndex("STATUS"))).setUpdatedTime(cursor.getString(cursor.getColumnIndex("UPDATEDTIME"))));
        }
        return invoices;
    }

    public Map<String, Integer> getInvoicesReportBasedOnMonth(String yearAndMonth) {
        SQLiteDatabase db = getWritableDatabase();
//        select invoiceDate,count(invoiceDate) from Invoices where invoiceDate like '2017-08-%' group by invoiceDate
        Cursor cursor = db.rawQuery("select invoiceDate,count(invoiceDate) as count from Invoices where invoiceDate like '?-%' group by invoiceDate", new String[]{yearAndMonth});
        Map<String, Integer> report = new LinkedHashMap<>();
        while (cursor.moveToNext()) {
            report.put(cursor.getString(cursor.getColumnIndex("INVOICEDATE")), cursor.getInt(cursor.getColumnIndex("count")));
        }
        return report;
    }

    public Map<String, Integer> getInvoicesBasedStatusesCount() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select status,count(status)as count from Invoices group by status", new String[]{});
        Map<String, Integer> report = new LinkedHashMap<>();
        while (cursor.moveToNext()) {
            report.put(cursor.getString(cursor.getColumnIndex("STATUS")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("count"))));
        }
        return report;
    }
}
