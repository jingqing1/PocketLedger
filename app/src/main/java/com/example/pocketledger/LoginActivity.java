package com.example.pocketledger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pocketledger.activity.MainActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        Button login = findViewById(R.id.btn_login);
        EditText account = findViewById(R.id.ed_e_mail);
        EditText password = findViewById(R.id.ed_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account.getText().toString().equals("123456") && password.getText().toString().equals("123456")){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });


    }
}