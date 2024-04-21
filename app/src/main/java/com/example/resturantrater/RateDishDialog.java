package com.example.resturantrater;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RateDishDialog extends AppCompatActivity {

    RadioGroup dishTypeRadioGroup;
    RatingBar dishRatingBar;

    Button rateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_dish_dialog);

        dishTypeRadioGroup = findViewById(R.id.dish_type_radio_group);
        dishRatingBar = findViewById(R.id.dish_rating_bar);
        rateButton = findViewById(R.id.rate_button);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDish();
            }
        });
    }


    private void rateDish() {
        // Get the selected dish type
        int selectedRadioButtonId = dishTypeRadioGroup.getCheckedRadioButtonId();
        String dishType = "";

        if (selectedRadioButtonId == R.id.appetizer_radio_button) {
            dishType = "Appetizer";
        } else if (selectedRadioButtonId == R.id.main_dish_radio_button) {
            dishType = "Main Dish";
        } else if (selectedRadioButtonId == R.id.dessert_radio_button) {
            dishType = "Dessert";
        }

        float rating = dishRatingBar.getRating();

        int restaurantId = getIntent().getIntExtra("restaurant_id", -1);

        // Create a Dish object with the details
        final Dish dish = new Dish("Dish Name", dishType, rating, restaurantId);

        // Save the dish to the database
        final RestaurantRaterDataSource dataSource = new RestaurantRaterDataSource(this);
        dataSource.open();
        final boolean isDishAdded = dataSource.addDish(dish);
        dataSource.close();

        // Show a dialog to confirm saving and ask if the user wants to rate another dish
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Dish");
        builder.setMessage("Do you want to rate another dish?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Show toast message
                Toast.makeText(RateDishDialog.this, "Rating another dish...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                recreate(); // Clears the activity and restarts it
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Go back to MainActivity to add a new restaurant
                dialog.dismiss();
                Intent intent = new Intent(RateDishDialog.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the RateDishDialog activity
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        // Show a toast message based on whether the dish was added successfully
        if (isDishAdded) {
            Toast.makeText(this, "Dish rated and saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to rate and save the dish. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


}

