package com.example.proyecto.RecoverPass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;

public class RecoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        TextView tvGoLogin = findViewById(R.id.tvGoLogin);
        tvGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnRecoverSend = findViewById(R.id.btnRecoverSend);
        btnRecoverSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverActivity.this, RecoverSendActivity.class);
                startActivity(intent);
            }
        });

    }

}
