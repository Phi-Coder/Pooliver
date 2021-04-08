package com.example.poolliver.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poolliver.R;

import static android.content.Context.MODE_PRIVATE;


public class tab3 extends Fragment {

    TextView username;
    public static final String SHARED_PREFS = "sharedPrefs";
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        mContext = view.getContext();
        username = view.findViewById(R.id.usernameA);

        // set name at person's profile
        setName();


        return view;
    }

    public void setName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        username.setText(name.toUpperCase());

    }
}