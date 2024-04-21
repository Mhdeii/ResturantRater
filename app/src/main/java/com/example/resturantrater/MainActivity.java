package com.example.resturantrater;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText restaurantNameEditText;
    EditText restaurantAddressEditText;
    Button saveButton;
    int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantNameEditText = findViewById(R.id.restaurant_name_edittext);
        restaurantAddressEditText = findViewById(R.id.restaurant_address_edittext);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestaurant();
            }
        });
    }

    private void saveRestaurant() {
        // Get restaurant details from EditText fields
        String restaurantName = restaurantNameEditText.getText().toString();
        String restaurantAddress = restaurantAddressEditText.getText().toString();

        Log.d("MainActivity", "Restaurant name: " + restaurantName);
        Log.d("MainActivity", "Restaurant address: " + restaurantAddress);

        RestaurantRaterDataSource dataSource = new RestaurantRaterDataSource(this);
        try {
            dataSource.open();

            boolean isRestaurantAdded = dataSource.addRestaurant(new Restaurant(restaurantName, restaurantAddress));

            if (isRestaurantAdded) {
                // Assign the restaurant ID after adding the restaurant
                restaurantId = getRestaurantIdFromDatabase(restaurantName);
                RateQuestionDialog dialog = new RateQuestionDialog(MainActivity.this, restaurantId);
                dialog.show();
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during database operations
            e.printStackTrace();
        } finally {
            dataSource.close();
        }
    }

    //getting id from db
    private int getRestaurantIdFromDatabase(String restaurantName) {
        int restaurantId = -1;

        // Open the database connection
        RestaurantRaterDataSource dataSource = new RestaurantRaterDataSource(this);
        try {
            dataSource.open();

            // Query the database to retrieve the restaurant ID based on the restaurant name
            restaurantId = dataSource.getRestaurantIdByName(restaurantName);
        } catch (Exception e) {
            // Handle any exceptions that occur during database operations
            e.printStackTrace();
        } finally {
            dataSource.close();
        }

        return restaurantId;
    }



}

