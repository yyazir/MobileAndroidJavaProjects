package com.yumel.rehber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String PHARMACY_SCHEMA = "PHARMACY_SCHEMA";
    public static final String PHARMACY = "PHARMACY";
    public static final String NAME = "name";
    public static final String DIST = "dist";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String LOCATION = "loc";
    public static final String ID = "id";
    public static final String DATE = "date";
    SQLiteDatabase db = this.getReadableDatabase();

    public DataBaseHelper(@Nullable Context context) {
        super(context, PHARMACY_SCHEMA, null, 1);
    }

    //yeni db burada oluşturuluyor.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PHARMACY + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " text," + DIST + " text," + ADDRESS + " text, " + PHONE + " text, " + LOCATION + " text, " + DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(PostPharmacy.Pharmacy pharmacy) {
        //String updateDate = "UPDATE PHARMACY SET date = CURRENT_DATE WHERE date IS NULL";
        //db.execSQL(updateDate);
        ContentValues cv = new ContentValues();
        cv.put(NAME, pharmacy.getName());
        cv.put(DIST, pharmacy.getDistrict());
        cv.put(ADDRESS, pharmacy.getAddress());
        cv.put(PHONE, pharmacy.getPhone());
        cv.put(LOCATION, pharmacy.getLocation());
        //cv.put(DATE, pharmacy.getDate());
        long insert = db.insert(PHARMACY, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }


    }

    public ArrayList<PostPharmacy.Pharmacy> getEveryone() {
        ArrayList<PostPharmacy.Pharmacy> returnList = new ArrayList<>();
        String query = "SELECT * FROM PHARMACY WHERE DATE(date) = CURRENT_DATE ORDER BY DIST DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //int pid = cursor.getInt(0);
                String pname = cursor.getString(1);
                String pdistrict = cursor.getString(2);
                String paddress = cursor.getString(3);
                String pphone = cursor.getString(4);
                String plocation = cursor.getString(5);
                PostPharmacy.Pharmacy newPharmacy = new PostPharmacy.Pharmacy(pname, pdistrict, paddress, pphone, plocation);
                returnList.add(newPharmacy);
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        return returnList;
    }

    public ArrayList<String> getDistrictList() {
        ArrayList<String> returnList = new ArrayList<String>();
        String queryDistrict = "SELECT DISTINCT dist FROM PHARMACY WHERE DATE(date) = CURRENT_DATE ORDER BY 1 ASC";
        Cursor cursorDistrict = db.rawQuery(queryDistrict, null);
        if (cursorDistrict != null && cursorDistrict.moveToFirst()) {
            do {
                String district = cursorDistrict.getString(0);
                returnList.add(district);
            }
            while (cursorDistrict.moveToNext());
        } else {

        }
        cursorDistrict.close();
        return returnList;

    }


    public ArrayList<String> getNeighborhoodList(String district) {
        ArrayList<String> returnList = new ArrayList<String>();
        String queryNeighborhood = "SELECT UPPER(address) FROM PHARMACY WHERE DATE(date) = CURRENT_DATE AND dist = " + "'" + district + "'" + " ORDER BY 1 ASC";
        Cursor cursorNeighborhood = db.rawQuery(queryNeighborhood, null);
        if (cursorNeighborhood != null && cursorNeighborhood.moveToFirst()) {
            do {
                String neighborhood = cursorNeighborhood.getString(0);
                String[] neighborhoodSplit = neighborhood.split(" MAH");
                returnList.add(neighborhoodSplit[0]);
            } while (cursorNeighborhood.moveToNext());

        } else {

        }
        cursorNeighborhood.close();
        return returnList;
    }

    public ArrayList<PostPharmacy.Pharmacy> getFilterPharmacy(String neighborhood) {
        ArrayList<PostPharmacy.Pharmacy> returnList = new ArrayList<>();
        String query = "SELECT * FROM PHARMACY WHERE  UPPER(address) LIKE " + "'%" + neighborhood + " MAH.%'" + " ORDER BY 1 ASC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //int pid = cursor.getInt(0);
                String pname = cursor.getString(1);
                String pdistrict = cursor.getString(2);
                String paddress = cursor.getString(3);
                String pphone = cursor.getString(4);
                String plocation = cursor.getString(5);
                PostPharmacy.Pharmacy newPharmacy = new PostPharmacy.Pharmacy(pname, pdistrict, paddress, pphone, plocation);
                returnList.add(newPharmacy);
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        return returnList;
    }


}


