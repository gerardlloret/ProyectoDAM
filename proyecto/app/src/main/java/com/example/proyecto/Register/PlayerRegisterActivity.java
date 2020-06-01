package com.example.proyecto.Register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Token;
import com.example.proyecto.NavigationController.ControllerActivity;
import com.example.proyecto.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerRegisterActivity extends AppCompatActivity {

    EditText playerRegisterUsername;
    EditText playerRegisterEmail;
    EditText playerRegisterPass;
    EditText playerRegisterPass2;
    Button btnPlayerRegisterCreate;

    //Metodo para obtener el token del sharedPreferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_register);

        playerRegisterUsername = findViewById(R.id.playerRegisterUsername);
        playerRegisterEmail = findViewById(R.id.playerRegisterEmail);
        playerRegisterPass = findViewById(R.id.playerRegisterPass);
        playerRegisterPass2 = findViewById(R.id.playerRegisterPass2);

        btnPlayerRegisterCreate = findViewById(R.id.btnPlayerRegisterCreate);
        btnPlayerRegisterCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobaciones()) {
                    crearJugador(playerRegisterUsername.getText().toString(), playerRegisterEmail.getText().toString(), playerRegisterPass.getText().toString());
                }
            }
        });

    }

    //Metodo para crear un jugador
    protected void crearJugador(final String alias, final String email, final String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/signUpJugador/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Token token = gson.fromJson(response, Token.class);
                        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", token.getToken());
                        editor.putString("email", email);
                        editor.putString("tipo", "jugador");
                        editor.apply();
                        pushToNavigationController();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        playerRegisterEmail.setError(getResources().getString(R.string.vEmailExist));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("alias", alias);
                params.put("email",email);
                params.put("password",password);
                return  params;
            }
        };
        queue.add(request);
    }

    //Metodo para validar los datos antes de enviarlos al backend
    public boolean comprobaciones(){
        boolean valido = true;
        if(!playerRegisterPass.getText().toString().equals(playerRegisterPass2.getText().toString())) {
            playerRegisterPass2.setError(getResources().getString(R.string.vPassMatch));
            valido = false;
        }
        if(playerRegisterUsername.getText().toString().length()<1||playerRegisterUsername.getText().toString().length()>30){
            playerRegisterUsername.setError(getResources().getString(R.string.vAlias));
            valido = false;
        }
        if(playerRegisterEmail.getText().toString().length()<1||playerRegisterEmail.getText().toString().length()>30){
            playerRegisterEmail.setError(getResources().getString(R.string.vEmail));
            valido = false;
        }
        if(playerRegisterPass.getText().toString().length()<1||playerRegisterPass.getText().toString().length()>30){
            playerRegisterPass.setError(getResources().getString(R.string.vPass));
            valido = false;
        }
        if(!Manager.emailValido(playerRegisterEmail.getText().toString())){
            playerRegisterEmail.setError(getResources().getString(R.string.vEmailFormat));
            valido = false;
        }
        return valido;
    }

    //Metodo para ir al navigation controller
    private void pushToNavigationController(){
        if (!obtenerToken().equalsIgnoreCase("def")) {
            Intent intent = new Intent(PlayerRegisterActivity.this, ControllerActivity.class);
            startActivity(intent);
        }
    }

}
