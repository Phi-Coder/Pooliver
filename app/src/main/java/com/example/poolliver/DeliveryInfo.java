package com.example.poolliver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeliveryInfo extends AppCompatActivity {

    TextView pickupAddress, dropAddress, Timings, ItemType, name, phoneNum, Price;
    Button Accept, PriceBid, getDirections;
    public static final String SHARED_PREFS = "sharedPrefs";
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    int i;

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
        String uid = getIntent().getStringExtra("uid");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Accept.setOnClickListener(v -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            if (i == 0) {
                dbRef = database.getReference("user").child(uid).child("post").child("accept").child("1").child("UserName");
            } else if (i == 1) {
                dbRef = database.getReference("user").child(uid).child("post").child("accept").child("2").child("UserName");
            } else if (i >= 2) {
                Accept.setEnabled(false);
            }

            String userUid = user.getUid();

            if (userUid.equals(uid)) {
                Toast.makeText(DeliveryInfo.this, "Cannot accept your own delivery", Toast.LENGTH_SHORT).show();

            } else if (userUid != uid) {
                Toast.makeText(DeliveryInfo.this, "accept request sent to the owner", Toast.LENGTH_SHORT).show();
                dbRef.setValue(setName());
                i++;
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

    public String setName() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        return name;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getParentActivityIntent();
        startActivity(intent);
        finishAffinity();
//        moveTaskToBack(true);
    }

}