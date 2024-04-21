package com.example.resturantrater;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class RateQuestionDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int restaurantId;

    public RateQuestionDialog(Context context, int restaurantId) {
        super(context);
        this.context = context;
        this.restaurantId = restaurantId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rate_question_dialog, null);
        setContentView(view);

        Button yesButton = view.findViewById(R.id.yes_button);
        Button noButton = view.findViewById(R.id.no_button);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.yes_button) {
            // Redirect to RateDishDialog and pass the restaurant ID
            Intent intent = new Intent(context, RateDishDialog.class);
            intent.putExtra("restaurant_id", restaurantId);
            context.startActivity(intent);
            dismiss();
        } else if (v.getId() == R.id.no_button) {
            // Redirect to MainActivity
            Intent mainIntent = new Intent(context, MainActivity.class);
            context.startActivity(mainIntent);
            // Finish the current activity (RateQuestionDialog)
            dismiss();
        }
    }
}
