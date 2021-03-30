package com.example.poolliver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeliveryInfo extends AppCompatActivity {

    TextView pickupAddress, dropAddress, Timings, ItemType, name, phoneNum, Price;
    Button Accept, PriceBid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);

        pickupAddress = findViewById(R.id.pickAdd);
        dropAddress = findViewById(R.id.dropAdd);
        Timings = findViewById(R.id.Time);
        ItemType = findViewById(R.id.ItemType);
        name = findViewById(R.id.Name);
        Price = findViewById(R.id.Price);
        Accept = findViewById(R.id.acceptRide);
        PriceBid = findViewById(R.id.priceBid);

        InncomingIntent();

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        PriceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void InncomingIntent() {
        String pickupaddress = getIntent().getStringExtra("pickupaddress");
        String dropaddress = getIntent().getStringExtra("dropaddress");
        String timings = getIntent().getStringExtra("timings");
        String itemtype = getIntent().getStringExtra("itemtype");
        String username = getIntent().getStringExtra("username");
        String price = getIntent().getStringExtra("price");

        fillData();

    }
    private void fillData(String pickupaddress, String dropaddress, String timings, String itemtype, String username, String price)
    {
        pickupAddress.setText(pickupaddress);
        dropAddress.setText(dropaddress);
        pickupAddress.setText(pickupAddress);
        pickupAddress.setText(pickupAddress);
        pickupAddress.setText(pickupAddress);
        pickupAddress.setText(pickupAddress);
    }
}