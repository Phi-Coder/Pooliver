package com.example.poolliver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveryInfo extends AppCompatActivity {

    TextView pickupAddress, dropAddress, Timings, ItemType, name, phoneNum, Price;
    Button Accept, PriceBid, getDirections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);

        pickupAddress = findViewById(R.id.pickAdd);
        dropAddress = findViewById(R.id.dropAdd);
        Timings = findViewById(R.id.Time);
        getDirections = findViewById(R.id.getDirections);
        ItemType = findViewById(R.id.ItemType);
        name = findViewById(R.id.Name);
        Price = findViewById(R.id.Price);
        Accept = findViewById(R.id.acceptRide);
        PriceBid = findViewById(R.id.priceBid);

        InncomingIntent();

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent Driverintent = new Intent();
//                startActivity(Driverintent);
                Toast.makeText(DeliveryInfo.this, "work under construction", Toast.LENGTH_SHORT).show();

            }
        });

        PriceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeliveryInfo.this, "work under construction", Toast.LENGTH_SHORT).show();
            }
        });

        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dropaddress = getIntent().getStringExtra("dropaddress");
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + String.valueOf(dropaddress)));
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });
    }

    private void InncomingIntent() {
        String pickupaddress = getIntent().getStringExtra("pickupaddress");
        String dropaddress = getIntent().getStringExtra("dropaddress");
        String timings = getIntent().getStringExtra("timings");
        String itemtype = getIntent().getStringExtra("itemtype");
        String username = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");

        fillData(pickupaddress, dropaddress, timings, itemtype, username, price);

    }

    private void fillData(String pickupaddress, String dropaddress, String timings, String itemtype, String username, String price) {
        pickupAddress.setText(pickupaddress);
        dropAddress.setText(dropaddress);
        Timings.setText(timings);
        ItemType.setText(itemtype);
        name.setText(username);
        Price.setText(price);
    }
}