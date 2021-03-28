package com.example.poolliver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class PriceEstimation extends AppCompatActivity {

    ImageButton subtractPrice, addPrice;
    EditText Price;
    TextView maxPrice, minPrice;
    Button findDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_estimation);

        subtractPrice = findViewById(R.id.sub);
        addPrice = findViewById(R.id.add);
        Price = findViewById(R.id.price);
        maxPrice = findViewById(R.id.maxPrice);
        minPrice = findViewById(R.id.minPrice);
        findDriver = findViewById(R.id.findDriver);

    }
}