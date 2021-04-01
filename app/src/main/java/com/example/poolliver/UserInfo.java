package com.example.poolliver;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class UserInfo extends AppCompatActivity {

    EditText username, phonenum;
    Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        username = findViewById(R.id.user_name);
        phonenum = findViewById(R.id.phone_num);
        getStarted = findViewById(R.id.getStarted);

        String phone_number = getIntent().getStringExtra("phoneNum");
        phonenum.setText(phone_number);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty()) {
                    username.setError("enter your name");
                } else {
                    Intent intent = new Intent(UserInfo.this, MainActivity.class);
                    intent.putExtra("name", String.valueOf(username.getText()));
                    intent.putExtra("phoneNumber", phone_number);
                    startActivity(intent);
                }
            }
        });

    }
}