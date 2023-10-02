package com.example.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAmount, editTextTenure, editTextInterestRate;
    private TextView textViewResult;

    @Override
    // Logic to run as soon as the activity is created (initialized)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Linking activity_main.xml layout to this class

        // Linking all TextView layout elements to this class
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextTenure = findViewById(R.id.editTextTenure);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEMI();
            } // Function call to calculate EMI when btn is clicked
        });
    }

    // Calculates EMI for user based on their inputs
    private void calculateEMI() {
        // Getting user inputs and assigning them to variables
        String amountStr = editTextAmount.getText().toString().trim();
        String tenureStr = editTextTenure.getText().toString().trim();
        String interestRateStr = editTextInterestRate.getText().toString().trim();

        if (amountStr.isEmpty() || tenureStr.isEmpty() || interestRateStr.isEmpty()) {
            // If all fields are empty and calculate btn is clicked, then display a pop-up input error message
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input Error");
            builder.setMessage("Please enter values in all fields.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create(); // using Android's alert pop-up
            dialog.show();
        } else {
            try {
                // String variables are converted to doubles and integers as specified below
                double principal = Double.parseDouble(amountStr);
                int tenureMonths = Integer.parseInt(tenureStr);
                double annualInterestRate = Double.parseDouble(interestRateStr);

                // Converting annual interest rate to monthly
                double monthlyInterestRate = (annualInterestRate / 100) / 12;

                // EMI calculation
                double emi = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths) /
                        (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

                String result = "Monthly EMI: $" + String.format("%.2f", emi);

                // Sending the user's EMI result to the ResultActivity class
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("emi_result", result);
                startActivity(intent);

            } catch (NumberFormatException e) { // Checks if input isnt a number format
                textViewResult.setText("Invalid input. Please enter valid numbers.");
            }
        }
    }
}