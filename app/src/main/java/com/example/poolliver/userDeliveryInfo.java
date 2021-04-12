package com.example.poolliver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poolliver.database.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class userDeliveryInfo extends AppCompatActivity {

    private TextView pickupAddress, dropAddress, Timings, ItemType, phoneNum, Price;
    ListView listView;
    private ArrayList<Request> arrayList;
    DatabaseReference dbRef;
    FirebaseDatabase db;
    DatabaseReference node;

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
        RequestAdapter requestAdapter = new RequestAdapter(this, R.layout.row, arrayList);

        db = FirebaseDatabase.getInstance();
//        node = db.getReference("user");
        Query query = db.getReference("user")
                .orderByChild("post/uid")
                .equalTo(uid);


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                for (DataSnapshot item : snapshot.getChildren()) {
                    for (DataSnapshot d : item.getChildren()) {
                        if (d.getKey().equals("accept")) {
                            for (int i = 1; i <= d.getChildrenCount(); i++) {
                                String username = d.child(String.valueOf(i)).child("username").getValue(String.class);
                                String price = d.child(String.valueOf(i)).child("price").getValue(String.class);
                                String phoneNum = d.child(String.valueOf(i)).child("phoneNum").getValue(String.class);
                                Request request = new Request(username, price, phoneNum);
                                arrayList.add(request);
                            }
                        }
                    }
                }
                requestAdapter.notifyDataSetChanged();
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

        listView.setAdapter(requestAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // getting the information of the person who accepted the delivery
//                query.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                AlertDialog.Builder alert = new AlertDialog.Builder(userDeliveryInfo.this);
                alert.setTitle("Assign this user to Take the Delivery");

                alert.setPositiveButton("Confirm", (dialog, whichButton) -> {
                    dbRef = FirebaseDatabase.getInstance().getReference("user").child(uid).child("post/accept");
                    dbRef.child(String.valueOf(id + 1)).child("ifAccepted").setValue(true);
                    listView.setEnabled(false);

                });

                alert.setNegativeButton("No",
                        (dialog, whichButton) -> {
                        });
                alert.show();

            }
        });

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

    class RequestAdapter extends ArrayAdapter<Request> {

        private static final String TAG = "Request Adapter";
        Context mContext;
        int mResource;

        public RequestAdapter(@NonNull Context mContext, int resource, @NonNull List<Request> objects) {
            super(mContext, resource, objects);
            this.mContext = mContext;
            this.mResource = resource;
        }

        @SuppressLint("ViewHolder")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String username = getItem(position).getUsername();
            String price = getItem(position).getPrice();
            String phoneNum = getItem(position).getPhoneNum();

            Request request = new Request(username, price, phoneNum);

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);

            TextView USERNAME = convertView.findViewById(R.id.USERNAME);
            TextView PRICE = convertView.findViewById(R.id.PRICE);
            TextView PHONENUM = convertView.findViewById(R.id.PHONENUM);

            USERNAME.setText(username);
            PRICE.setText(price);
            PHONENUM.setText(phoneNum);
            return convertView;
        }
    }


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