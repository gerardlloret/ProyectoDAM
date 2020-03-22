package com.example.proyecto.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;

public class SelectionActivity extends AppCompatActivity {
    ImageView ivUser;
    ImageView ivTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        ivUser = (ImageView)findViewById(R.id.ivUser);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, PlayerRegisterActivity.class);
                startActivity(intent);
            }
        });

        ivTeam = (ImageView)findViewById(R.id.ivTeam);
        ivTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, TeamRegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
