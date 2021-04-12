package com.example.poolliver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DeliveryAccepted extends AppCompatActivity {

    TextView pickupAddress, dropAddress, Timings, ItemType, name, phoneNum, Price, empty;
    ScrollView scrollView;
    Button getDirections;
    RelativeLayout notificationLayout;
    public static final String SHARED_PREFS = "sharedPrefs";
    DatabaseReference dbRef;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_accepted);

        empty = findViewById(R.id.empty);
        scrollView = findViewById(R.id.scrollView);

        pickupAddress = findViewById(R.id.pickAdd);
        phoneNum = findViewById(R.id.phonenum);
        dropAddress = findViewById(R.id.dropAdd);
        Timings = findViewById(R.id.Time);
        getDirections = findViewById(R.id.getDirections);
        ItemType = findViewById(R.id.ItemType);
        name = findViewById(R.id.Name);
        Price = findViewById(R.id.Price);
        notificationLayout = findViewById(R.id.notificationLayout);

        dbRef = FirebaseDatabase.getInstance().getReference("user").child("post/accept");
        db = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        scrollView.setVisibility(View.INVISIBLE);

        for (int i = 1; i <= 3; i++) {
            Query query = db.getReference("user")
                    .orderByChild("post/accept/" + String.valueOf(i) + "/userID")
                    .equalTo(uid);

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.i("accept", String.valueOf(snapshot.child("post").getValue()));
                    String nameF = snapshot.child("post/name").getValue(String.class);
                    String pickupAddressF = snapshot.child("post/pickupAddress").getValue(String.class);
                    String dropAddressF = snapshot.child("post/dropAddress").getValue(String.class);
                    String pickupLatF = snapshot.child("post/pickupLat").getValue(String.class);
                    String pickupLongF = snapshot.child("post/pickupLong").getValue(String.class);
                    String dropLatF = snapshot.child("post/dropLat").getValue(String.class);
                    String dropLongF = snapshot.child("post/dropLong").getValue(String.class);
                    String phoneNumberF = snapshot.child("post/phoneNumber").getValue(String.class);
                    String itemtypeF = snapshot.child("post/itemtype").getValue(String.class);
                    String timeF = snapshot.child("post/time").getValue(String.class);
                    String priceF = snapshot.child("post/price").getValue(String.class);

                    name.setText(nameF);
                    pickupAddress.setText(pickupAddressF);
                    dropAddress.setText(dropAddressF);
                    phoneNum.setText(phoneNumberF);
                    ItemType.setText(itemtypeF);
                    Timings.setText(timeF);
                    Price.setText(priceF);

                    getDirections.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uri = "http://maps.google.com/maps?hl=en&saddr=" + pickupLatF + "," + pickupLongF + "&daddr=" + dropLatF + ","
                                    + dropLongF + "&mode=driving";
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                            startActivity(i);
                        }

                    });
                    empty.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getParentActivityIntent();
        startActivity(intent);
    }
}