package com.example.marij.probaana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marij on 25.1.2018..
 */


public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";

    static final String KEY_STREET = "street";
    static final String KEY_HOUSE_NO = "house_number";
    static final String KEY_CITY = "city";
    static final String KEY_CONTACT_ID = "contact_id";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final String DATABASE_ADDRESS_TABLE = "address";
    static final int DATABASE_VERSION = 3;

    static final String DATABASE_CREATE_CONTACTS_TABLE=
            "create table contacts (_id integer primary key autoincrement, "
                    + "name text not null, email text not null);";

    static final String DATABASE_CREATE_ADDR_TABLE=
            "create table address (_id integer primary key autoincrement, "
                    + "contact_id integer not null, "
                    + "street text not null, house_number integer not null, "
                    + "city text not null);";


    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE_CONTACTS_TABLE);
                db.execSQL(DATABASE_CREATE_ADDR_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS contacts");
            db.execSQL("DROP TABLE IF EXISTS address");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertContact(String name, String email)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //--insert address into table--
    public long insertAddress(Integer contactId, String street, Integer houseNo, String city)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CONTACT_ID, contactId);
        initialValues.put(KEY_STREET, street);
        initialValues.put(KEY_HOUSE_NO, houseNo);
        initialValues.put(KEY_CITY, city);
        return db.insert(DATABASE_ADDRESS_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //--deletes address for contact--
    public boolean deleteAddress(long rowId)
    {
        return db.delete(DATABASE_ADDRESS_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL}, null, null, null, null, null);
    }

    //---retrieves all the addresses---
    public Cursor getAllAddresses()
    {
        return db.query(DATABASE_ADDRESS_TABLE, new String[] {KEY_ROWID, KEY_CONTACT_ID,
                KEY_STREET, KEY_HOUSE_NO, KEY_CITY}, null, null, null, null,null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //--retrieves a particular address--
    public Cursor getAddress(long contactId) throws SQLException
    {
        Cursor mCursor =
                db.query(DATABASE_ADDRESS_TABLE, new String[] {KEY_ROWID, KEY_CONTACT_ID,
                                KEY_STREET, KEY_HOUSE_NO, KEY_CITY}, KEY_CONTACT_ID + "=" + contactId, null,
                        null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //--updates address--
    public boolean updateAddress(long rowId, long contactId, String street, long houseNo, String city)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_CONTACT_ID, contactId);
        args.put(KEY_STREET, street);
        args.put(KEY_HOUSE_NO, houseNo);
        args.put(KEY_CITY, city);
        return db.update(DATABASE_ADDRESS_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}

