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

    private void shareEMIResult(String emiResult) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "EMI Result: " + emiResult);

        // Optionally, you can set a subject for the shared content
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EMI Calculation Result");

        startActivity(Intent.createChooser(shareIntent, "Share EMI Result"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewEMIResult = findViewById(R.id.textViewEMIResult);
        Button buttonShare = findViewById(R.id.buttonShare);

        // Retrieve the EMI result from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String emiResult = intent.getStringExtra("emi_result");
            if (emiResult != null) {
                textViewEMIResult.setText(emiResult);
            }
        }
        // Set OnClickListener for the Share button
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emiResult = intent.getStringExtra("emi_result");
                shareEMIResult(emiResult); // Replace emiResult with the actual EMI result variable
            }
        });

        // Enable the "Back" arrow in the custom action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Set a custom icon for the "Back" arrow if needed
            // actionBar.setHomeAsUpIndicator(R.drawable.custom_back_icon);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Handle the back button click here, send the user back to the home screen
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}