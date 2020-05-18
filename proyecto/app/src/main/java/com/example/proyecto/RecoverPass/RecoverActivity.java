package com.example.proyecto.RecoverPass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.API.JavaMailAPI;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;

import java.nio.charset.Charset;
import java.util.Random;

public class RecoverActivity extends AppCompatActivity {

    EditText etRecoverActivityEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        etRecoverActivityEmail = findViewById(R.id.etRecoverActivityEmail);

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
                if (Manager.emailValido(etRecoverActivityEmail.getText().toString())){
                    sendEmail(etRecoverActivityEmail.getText().toString(), generateRandomPassword());
                    Intent intent = new Intent(RecoverActivity.this, RecoverSendActivity.class);
                    startActivity(intent);
                }else{
                    etRecoverActivityEmail.setError("Este email no tiene un formato valido");
                }
            }
        });

    }

    protected void sendEmail(String mail, String pass) {
        //String mail = "gerardlloreteij@gmail.com";
        String subject = "Regeneracion de password";
        String message = "Tu nuevo password es: " + pass;
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }

    protected String generateRandomPassword() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }


}
