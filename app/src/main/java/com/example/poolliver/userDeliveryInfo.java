package com.example.poolliver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.poolliver.database.dataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Arrays;

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
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row, arrayList);

        db = FirebaseDatabase.getInstance();
        node = db.getReference("user");
        Query query = db.getReference("user")
                .orderByChild("post/uid")
                .equalTo(uid);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.child("post").child("accept/1/UserName").getValue(String.class) != null) {
                    String string = snapshot.child("post").child("accept").getValue(String.class);
                    arrayList.add(string);
                }
//                if (snapshot.child("post").child("accept/2/UserName").getValue(String.class) != null) {
//                    String string1 = snapshot.child("post").child("accept/2/UserName").getValue(String.class);
////                    arrayList.add(string1);
//                }

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