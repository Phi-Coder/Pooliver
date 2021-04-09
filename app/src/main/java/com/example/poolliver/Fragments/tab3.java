package com.example.poolliver.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poolliver.Adapters.RecyclerViewAdapter;
import com.example.poolliver.Adapters.userActivityRecyclerAdapter;
import com.example.poolliver.R;
import com.example.poolliver.database.dataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class tab3 extends Fragment {

    TextView username;
    public static final String SHARED_PREFS = "sharedPrefs";
    Context mContext;
    RecyclerView userRecyclerView;
    userActivityRecyclerAdapter userRecyclerAdapter;
    List<dataHolder> userItemList;
    DatabaseReference node;
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        mContext = view.getContext();
        username = view.findViewById(R.id.usernameA);
        userRecyclerView = view.findViewById(R.id.userRecyclerView);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // set name at person's profile
        setName();

        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userItemList = new ArrayList<>();

        db = FirebaseDatabase.getInstance();
        node = db.getReference("user");
        Query query = db.getReference("user")
                .orderByChild("post/uid")
                .equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userItemList.clear();

                for (DataSnapshot itemSnap : snapshot.getChildren()) {
                    dataHolder data = itemSnap.child("post").getValue(dataHolder.class);
                    userItemList.add(data);
                }
                userRecyclerAdapter = new userActivityRecyclerAdapter(userItemList, getContext());
                userRecyclerAdapter.notifyDataSetChanged();
                userRecyclerView.setAdapter(userRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + "error.getMessage() ");
            }
        });


        return view;
    }

    public void setName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        username.setText(name.toUpperCase());

    }
}