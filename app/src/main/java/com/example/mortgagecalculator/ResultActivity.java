package com.example.mortgagecalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultActivity extends AppCompatActivity {

    // Function handling the sharing of the user's EMI result
    private void shareEMIResult(String emiResult) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND); // uses android default Action Send share pop-up
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, emiResult);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EMI Calculation Result");
        startActivity(Intent.createChooser(shareIntent, "Share EMI Result"));
    }

    @Override
    // Logic to run as soon as the activity is created (initialized)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result); // Links the activity_result layout
        TextView textViewEMIResult = findViewById(R.id.textViewEMIResult); // Gets the user's EMI result
        Button buttonShare = findViewById(R.id.buttonShare); // Defining the share button

        // Retrieving the EMI result from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String emiResult = intent.getStringExtra("emi_result"); // Placing the EMI result in a string
            if (emiResult != null) {
                textViewEMIResult.setText(emiResult);
            }
        }
        // Checking if share button is clicked
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent(); // Once the btn is clicked, we get the intent initialized
                String emiResult = intent.getStringExtra("emi_result");
                shareEMIResult(emiResult); // function call
            }
        });

        // Enable the "Back" arrow in the custom action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish(); // Sends the user back to home screen
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}