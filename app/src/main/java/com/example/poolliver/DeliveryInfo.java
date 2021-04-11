package com.example.poolliver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;


public class DeliveryInfo extends AppCompatActivity {

    TextView pickupAddress, dropAddress, Timings, ItemType, name, phoneNum, Price;
    Button Accept, getDirections;
    public static final String SHARED_PREFS = "sharedPrefs";
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    int i;
    long maxID = 0;

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

        InncomingIntent();
        String price = getIntent().getStringExtra("price");
        // id of user who has created the delivery
        String uid = getIntent().getStringExtra("uid");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("user").child(uid).child("post/accept");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxID = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", error.getMessage());
            }
        });

        Accept.setOnClickListener(v -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            String userUid = user.getUid();

            if (userUid.equals(uid)) {
                Toast.makeText(DeliveryInfo.this, "Cannot accept your own delivery", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(DeliveryInfo.this, "accept request sent to the owner", Toast.LENGTH_SHORT).show();
                dbRef.child(String.valueOf(maxID + 1)).child("username").setValue(setName());
                dbRef.child(String.valueOf(maxID + 1)).child("price").setValue(price);
                dbRef.child(String.valueOf(maxID + 1)).child("userID").setValue(userUid);
                dbRef.child(String.valueOf(maxID + 1)).child("phoneNum").setValue(String.valueOf(mAuth.getCurrentUser().getPhoneNumber()));
            }


        });

        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dropaddress = getIntent().getStringExtra("dropaddress");
                String dropLat = getIntent().getStringExtra("dropLat");
                String dropLong = getIntent().getStringExtra("dropLong");
                String pickupLat = getIntent().getStringExtra("pickupLat");
                String pickupLong = getIntent().getStringExtra("pickupLong");
                String uri = "http://maps.google.com/maps?hl=en&saddr=" + pickupLat + "," + pickupLong + "&daddr=" + dropLat + ","
                        + dropLong + "&mode=driving";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
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