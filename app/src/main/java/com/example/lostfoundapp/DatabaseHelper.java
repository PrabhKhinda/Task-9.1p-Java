package com.example.lostfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LostAdsDB";
    private static final String TABLE_NAME = "lost_ads";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void removeAdvert(int advertId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(advertId)});
        db.close();
    }

    public long insertAdvert(String type, String name, String description, String date, String location, String phone, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public List<Advert> getLostAds() {
        List<Advert> adsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = 'Lost'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                    String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                    String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                    double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
                    double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));

                    Advert advert = new Advert(id, type, name, description, date, location, phone, latitude, longitude);
                    adsList.add(advert);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();

        return adsList;
    }

    public List<Advert> getFoundAds() {
        List<Advert> adsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = 'Found'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                    String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                    String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                    double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
                    double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));

                    Advert advert = new Advert(id, type, name, description, date, location, phone, latitude, longitude);
                    adsList.add(advert);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();

        return adsList;
    }
}
