package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.NavigationController.ControllerActivity;
import com.example.proyecto.RecoverPass.RecoverActivity;
import com.example.proyecto.Register.SelectionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CAMBIAR PANTALLA PRINCIPAL
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerActivity.class);
                startActivity(intent);
            }
        });

        //CAMBIAR PANTALLA REGISTER
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                startActivity(intent);
            }
        });

        TextView tvRecover = findViewById(R.id.tvRecover);
        tvRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecoverActivity.class);
                startActivity(intent);
            }
        });
    }
}
