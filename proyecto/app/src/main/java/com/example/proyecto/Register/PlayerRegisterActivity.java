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

public class PlayerRegisterActivity extends AppCompatActivity {

    EditText playerRegisterUsername;
    EditText playerRegisterEmail;
    EditText playerRegisterPass;
    EditText playerRegisterPass2;
    Button btnPlayerRegisterCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_register);

        //Manager manager = new Manager();

        //manager.checkJugadorId("1");

        playerRegisterUsername = findViewById(R.id.playerRegisterUsername);
        playerRegisterEmail = findViewById(R.id.playerRegisterEmail);
        playerRegisterPass = findViewById(R.id.playerRegisterPass);

        btnPlayerRegisterCreate = findViewById(R.id.btnPlayerRegisterCreate);
        btnPlayerRegisterCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearJugador(playerRegisterUsername.getText().toString(), playerRegisterEmail.getText().toString(), playerRegisterPass.getText().toString());
                if (!obtenerToken().equalsIgnoreCase("def")) {
                    Intent intent = new Intent(PlayerRegisterActivity.this, ControllerActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    //Metodo para el crear un jugador
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
                        editor.apply();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("jugador_id", "16");
                params.put("alias", alias);
                params.put("email",email);
                params.put("password",password);
                return  params;
            }
        };
        queue.add(request);
    }

    //Metodo para obtener el token del shared preferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }
}
