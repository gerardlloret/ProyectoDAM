package com.example.proyecto.RecoverPass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;

public class RecoverSendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_send);

        Button btnGoLogin = findViewById(R.id.btnGoLogin);
        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverSendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
