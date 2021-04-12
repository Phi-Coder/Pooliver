package com.example.poolliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PriceEstimation extends AppCompatActivity {

    ImageButton subtractPrice, addPrice;
    EditText Price;
    int priceFinal;
    TextView maxPrice, minPrice;
    Button findDriver;
    public static final String SHARED_PREFS = "sharedPrefs";

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
        String time = getIntent().getStringExtra("time");
        String pickupLat = getIntent().getStringExtra("pickupLat");
        String pickupLong = getIntent().getStringExtra("pickupLong");
        String dropLat = getIntent().getStringExtra("dropLat");
        String dropLong = getIntent().getStringExtra("dropLong");

        priceFinal = getIntent().getIntExtra("price", 0);
        final int MaxP = priceFinal + 15;
        final int MinP = priceFinal - 10;


        maxPrice.setText(String.valueOf(MaxP));
        if (MinP < 10) {
            minPrice.setText(String.valueOf(10));
        } else {
            minPrice.setText(String.valueOf(MinP));
        }
        if (priceFinal <= 10) {
            Price.setText(String.valueOf(10));
        } else {
            Price.setText(String.valueOf(priceFinal));
        }

        addPrice.setOnClickListener(v -> {
            if (priceFinal >= MaxP) {
                priceFinal = priceFinal;
            } else {
                priceFinal = priceFinal + 1;
            }
            Price.setText(String.valueOf(priceFinal));

        });

        subtractPrice.setOnClickListener(v -> {

            if (priceFinal <= MinP) {
                priceFinal = priceFinal;
            } else {
                priceFinal = priceFinal - 1;
            }
            Price.setText(String.valueOf(priceFinal));
        });

        findDriver.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String name = sharedPreferences.getString("name", "");
            String phoneNumber = sharedPreferences.getString("phoneNumber", "");
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            HashMap<String, Object> map = new HashMap<>();
            map.put("pickupAddress", String.valueOf(pickupaddress));
            map.put("dropAddress", String.valueOf(dropaddress));
            map.put("price", String.valueOf(priceFinal));
            map.put("time", time);
            map.put("name", name);
            map.put("itemtype", String.valueOf(itemtype));
            map.put("phoneNumber", phoneNumber);
            map.put("uid", uid);
            map.put("pickupLat", pickupLat);
            map.put("pickupLong", pickupLong);
            map.put("dropLat", dropLat);
            map.put("dropLong", dropLong);
            FirebaseDatabase.getInstance().getReference("user").child(uid).child("post").setValue(map);

            Intent intentFinal = new Intent(PriceEstimation.this, MainActivity.class);
            startActivity(intentFinal);

        });
    }
}
