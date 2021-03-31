package com.example.poolliver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PriceEstimation extends AppCompatActivity {

    ImageButton subtractPrice, addPrice;
    DatabaseReference mDatabase;
    EditText Price;
    int priceFinal;
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

        String pickupaddress = getIntent().getStringExtra("pickupAddress");
        String dropaddress = getIntent().getStringExtra("dropAddress");
        String itemtype = getIntent().getStringExtra("productType");

        priceFinal = getIntent().getIntExtra("price", 0);
        final int MaxP = priceFinal + 15;
        final int MinP = priceFinal - 10;

        Price.setText(String.valueOf(priceFinal));

        maxPrice.setText(String.valueOf(MaxP));
        minPrice.setText(String.valueOf(MinP));

        addPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceFinal >= MaxP) {
                    priceFinal = priceFinal;
                } else {
                    priceFinal = priceFinal + 1;
                }
                Price.setText(String.valueOf(priceFinal));

            }
        });

        subtractPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (priceFinal <= MinP) {
                    priceFinal = priceFinal;
                } else {
                    priceFinal = priceFinal - 1;
                }
                Price.setText(String.valueOf(priceFinal));
            }
        });

        findDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("pickupAddress", String.valueOf(pickupaddress));
                map.put("dropAddress", String.valueOf(dropaddress));
                map.put("price", priceFinal);
                map.put("itemtype", String.valueOf(itemtype));
                FirebaseDatabase.getInstance().getReference("user").push().child("post").updateChildren(map);

                Intent intentFinal = new Intent(PriceEstimation.this, MainActivity.class);
                startActivity(intentFinal);

            }
        });
    }
}
