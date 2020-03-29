package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyecto.Model.Token;
import com.example.proyecto.NavigationController.ControllerActivity;
import com.example.proyecto.RecoverPass.RecoverActivity;
import com.example.proyecto.Register.SelectionActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        //CAMBIAR PANTALLA PRINCIPAL
        Button btnLogin = findViewById(R.id.btnLogin);
        login(etEmail.getText().toString(), etPass.getText().toString());
        if(!obtenerToken().equalsIgnoreCase("def")) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ControllerActivity.class);
                    startActivity(intent);
                }
            });
        }

        //CAMBIAR PANTALLA REGISTER
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                startActivity(intent);
            }
        });

        //CAMBIAR PANTALLA RECOVER
        TextView tvRecover = findViewById(R.id.tvRecover);
        tvRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecoverActivity.class);
                startActivity(intent);
            }
        });
    }

    //Metodo para el login, si las credenciales son correctas setea un token en el sSharedPreferences
    protected void login(final String email, final String pass){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "ruta_que_aun_no_se";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Token token = gson.fromJson(response, Token.class);
                        Log.d("gla", token.getToken());
                        if(token.getError().equalsIgnoreCase("")){
                            SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token.getToken());
                            editor.apply();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", pass);
                return params;
            }
        };
        queue.add(request);
    }

    //Metodo para obtener el token del shared preferences
    private String obtenerToken(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }
}
