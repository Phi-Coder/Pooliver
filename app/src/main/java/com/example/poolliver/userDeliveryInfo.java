package com.example.poolliver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poolliver.database.dataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class userDeliveryInfo extends AppCompatActivity {

    private TextView pickupAddress, dropAddress, Timings, ItemType, phoneNum, Price;
    ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    String[] mName = {};
    String[] mPrice = {};
    FirebaseUser firebaseUser;
    FirebaseDatabase db;
    DatabaseReference node;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivery_info);

        pickupAddress = findViewById(R.id.UpickAdd);
        dropAddress = findViewById(R.id.UdropAdd);
        Timings = findViewById(R.id.UTime);
        ItemType = findViewById(R.id.UItemType);
        Price = findViewById(R.id.UPrice);
        listView = findViewById(R.id.listView);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.USERNAME, arrayList);

        db = FirebaseDatabase.getInstance();
        node = db.getReference("user");
        Query query = db.getReference("user")
                .orderByChild("post/uid")
                .equalTo(uid);


        query.addChildEventListener(new ChildEventListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                for (DataSnapshot item : snapshot.getChildren()) {
                    for (DataSnapshot d : item.getChildren()) {
                        if (d.getKey().equals("accept")) {
                            Log.i("snapshot", String.valueOf(d.getChildrenCount()));
                            for (int i = 1; i <= d.getChildrenCount(); i++) {
                                String string = d.child(String.valueOf(i)).getValue(String.class);
                                arrayList.add(string);
                            }
                        }
                    }
                }
                arrayAdapter.notifyDataSetChanged();
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

        listView.setAdapter(arrayAdapter);

        InncomingIntent();
    }

    private void InncomingIntent() {
        String pickupaddress = getIntent().getStringExtra("pickupaddress");
        String dropaddress = getIntent().getStringExtra("dropaddress");
        String timings = getIntent().getStringExtra("timings");
        String itemtype = getIntent().getStringExtra("itemtype");
        String price = getIntent().getStringExtra("price");

        fillData(pickupaddress, dropaddress, timings, itemtype, price);

    }

    private void fillData(String pickupaddress, String dropaddress, String timings, String itemtype, String price) {
        pickupAddress.setText(pickupaddress);
        dropAddress.setText(dropaddress);
        Timings.setText(timings);
        ItemType.setText(itemtype);
        Price.setText(price);
    }

//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//        String[] name;
//        String[] price;
//
//        public MyAdapter(@NonNull Context context, int resource, String[] name, String[] price) {
//            super(context, resource);
//            this.context = context;
//            this.name = name;
//            this.price = price;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View row = layoutInflater.inflate(R.layout.row, parent, false);
//            TextView USERNAME = findViewById(R.id.USERNAME);
//            TextView PRICE = findViewById(R.id.PRICE);
//
//            USERNAME.setText(name[position]);
//            PRICE.setText(price[position]);
//            return row;
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
        Intent intent = getParentActivityIntent();
        startActivity(intent);
//        finish();
//        finishAffinity();
    }
}