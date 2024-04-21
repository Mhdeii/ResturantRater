package com.example.resturantrater;

import static com.example.resturantrater.RestaurantRaterDBHelper.TABLE_RESTAURANTS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class RestaurantRaterDataSource {

    SQLiteDatabase database;
    RestaurantRaterDBHelper dbHelper;

    public RestaurantRaterDataSource(Context context) {
        dbHelper = new RestaurantRaterDBHelper(context);
    }

    public void open() throws SQLException {
        //log codes because there was an error and found it
        Log.d("DataSource", "Opening database...");
        database = dbHelper.getWritableDatabase();
        Log.d("DataSource", "Database opened successfully.");
    }

    public void close() {
        Log.d("DataSource", "Closing database...");
        dbHelper.close();
        Log.d("DataSource", "Database closed successfully.");
    }

    public boolean addRestaurant(Restaurant r) {
        boolean didSucceed = false;
        ContentValues initialValues = new ContentValues();

        try {
            open(); // Open the database before adding a restaurant

            Log.d("DataSource", "Adding restaurant: " + r.getName());

            initialValues.put("name", r.getName());
            initialValues.put("street_address", r.getStreetAddress());
            initialValues.put("city", r.getCity());
            initialValues.put("state", r.getState());
            initialValues.put("zipcode", r.getZipcode());

            didSucceed = database.insert(TABLE_RESTAURANTS, null, initialValues) > 0;

            if (didSucceed) {
                Log.d("DataSource", "Restaurant added successfully.");
            } else {
                Log.d("DataSource", "Failed to add restaurant.");
            }

        } catch (Exception e) {
            Log.e("DataSource", "Error adding restaurant: " + e.getMessage());
            didSucceed = false;
        } finally {
            close(); // Close the database after adding the restaurant
        }

        return didSucceed;
    }

    public boolean addDish(Dish d) {
        boolean didSucceed = false;
        ContentValues initialValues = new ContentValues();

        try {
            open(); // Open the database before adding a dish

            Log.d("DataSource", "Adding dish: " + d.getName());

            initialValues.put("name", d.getName());
            initialValues.put("type", d.getType());
            initialValues.put("rating", d.getRating());
            initialValues.put("restaurant_id", d.getRestaurantId());

            didSucceed = database.insert(RestaurantRaterDBHelper.TABLE_DISHES, null, initialValues) > 0;

            if (didSucceed) {
                Log.d("DataSource", "Dish added successfully.");
            } else {
                Log.d("DataSource", "Failed to add dish.");
            }

        } catch (Exception e) {
            Log.e("DataSource", "Error adding dish: " + e.getMessage());
            didSucceed = false;
        } finally {
            close(); // Close the database after adding the dish
        }

        return didSucceed;
    }

    public int getRestaurantIdByName(String restaurantName) {
        int restaurantId = -1; // Initialize the restaurant ID

        Cursor cursor = null;
        try {
            // Query the database to retrieve the restaurant ID based on the restaurant name
            String query = "SELECT restaurant_id FROM " + TABLE_RESTAURANTS + " WHERE name = ?";
            cursor = database.rawQuery(query, new String[]{restaurantName});

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("restaurant_id");
                if (columnIndex != -1) {
                    restaurantId = cursor.getInt(columnIndex);
                }
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during database query
            e.printStackTrace();
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }

        return restaurantId;
    }


}


