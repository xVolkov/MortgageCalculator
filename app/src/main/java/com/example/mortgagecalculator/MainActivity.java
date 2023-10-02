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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextTenure = findViewById(R.id.editTextTenure);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEMI();
            }
        });
    }

    private void calculateEMI() {
        String amountStr = editTextAmount.getText().toString().trim();
        String tenureStr = editTextTenure.getText().toString().trim();
        String interestRateStr = editTextInterestRate.getText().toString().trim();

        if (amountStr.isEmpty() || tenureStr.isEmpty() || interestRateStr.isEmpty()) {
            // All fields are empty, display a pop-up message
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input Error");
            builder.setMessage("Please enter values in all fields.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            try {
                double principal = Double.parseDouble(amountStr);
                int tenureMonths = Integer.parseInt(tenureStr);
                double annualInterestRate = Double.parseDouble(interestRateStr);

                // Convert annual interest rate to monthly interest rate
                double monthlyInterestRate = (annualInterestRate / 100) / 12;

                // Calculate EMI
                double emi = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths) /
                        (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

                String result = "Monthly EMI: $" + String.format("%.2f", emi);

                // Pass the EMI result to the ResultActivity
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("emi_result", result);
                startActivity(intent);

            } catch (NumberFormatException e) {
                textViewResult.setText("Invalid input. Please enter valid numbers.");
            }
        }
    }
}