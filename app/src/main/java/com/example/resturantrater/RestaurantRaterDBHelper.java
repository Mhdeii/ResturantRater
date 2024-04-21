package com.example.resturantrater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantRaterDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant_rater.db";
    private static final int VERSION_NUMBER = 1;
    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String TABLE_DISHES = "dishes";

    private static final String CREATE_RESTAURANTS_TABLE =
            "CREATE TABLE " + TABLE_RESTAURANTS + " (" +
                    "restaurant_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "street_address TEXT, " +
                    "city TEXT, " +
                    "state TEXT, " +
                    "zipcode TEXT); ";


    private static final String CREATE_DISHES_TABLE =
            "CREATE TABLE " + TABLE_DISHES + " (" +
                    "dish_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "type TEXT, " +
                    "rating REAL, " +
                    "restaurant_id INTEGER, " +
                    "FOREIGN KEY(restaurant_id) REFERENCES " + TABLE_RESTAURANTS + "(restaurant_id));";



    public RestaurantRaterDBHelper (Context context){
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESTAURANTS_TABLE);
        db.execSQL(CREATE_DISHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
