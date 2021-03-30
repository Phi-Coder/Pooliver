package com.example.poolliver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        String MaxPriceStr = maxPrice.getText().toString();
        int MaxP = Integer.parseInt(MaxPriceStr);

        String MinPriceStr = minPrice.getText().toString();
        int MinP = Integer.parseInt(MinPriceStr);

        addPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceStr = Price.getText().toString();
                int p = Integer.parseInt(priceStr);
                if (p >= MaxP) {
                    p = p;
                } else {
                    p = p + 1;
                }
                Price.setText(String.valueOf(p));
            }
        });

        subtractPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String priceStr = Price.getText().toString();
                int p = Integer.parseInt(priceStr);
                if (p <= MinP) {
                    p = p;
                } else {
                    p = p - 1;
                }
                Price.setText(String.valueOf(p));
            }
        });

    }
}