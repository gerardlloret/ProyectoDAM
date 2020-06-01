package com.example.proyecto.RecoverPass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.API.JavaMailAPI;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Equipo;
import com.example.proyecto.Model.Jugador;
import com.example.proyecto.Model.Token;
import com.example.proyecto.R;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RecoverActivity extends AppCompatActivity {

    EditText etRecoverActivityEmail;

    //Metodo para obtener el token del shared preferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }

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
                jugadorExists(etRecoverActivityEmail.getText().toString());
            }
        });

    }

    //Metodo para mandar el email con el nuevo password
    protected void sendEmail(String mail, String pass) {
        String subject = "Password recovery";
        String message = "Your new password is: " + pass;
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
        Intent intent = new Intent(RecoverActivity.this, RecoverSendActivity.class);
        startActivity(intent);
    }

    //Metodo para comprobar si existe un jugador con ese email
    protected void jugadorExists(final String email) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugadorExists/";
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
                        editor.apply();
                        obtenerJugador(obtenerToken(), email);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error");
                        equipoExists(email);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return  params;
            }
        };
        queue.add(request);
    }

    //Metodo para comprobar si existe un equipo con ese email
    protected void equipoExists(final String email) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/equipoExists/";
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
                        editor.apply();
                        obtenerEquipo(obtenerToken(), email);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error");
                        etRecoverActivityEmail.setError("No existe un usuario con este email");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return  params;
            }
        };
        queue.add(request);
    }

    //Metodo para obtener un jugador a partir de su email
    protected void obtenerJugador(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugador/"+email;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Jugador jugador = gson.fromJson(response, Jugador.class);
                        jugador.setPassword(Manager.generateRandomPassword());
                        updateJugador(email, jugador, obtenerToken());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("flx", "ERROR: " + error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Token " + token);
                return  header;
            }
        };
        queue.add(request);
    }

    //Metodo para actualizar el jugador con el nuevo password
    protected void updateJugador(final String email, final Jugador jugador, final String token){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugador/"+email;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        sendEmail(email, jugador.getPassword());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("flx", "ERROR: " + error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Token " + token);
                return  header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", jugador.getEmail());
                params.put("nombre", jugador.getNombre());
                params.put("alias", jugador.getAlias());
                params.put("password", jugador.getPassword());
                System.out.println(jugador.getImagen().length());
                params.put("imagen", jugador.getImagen());
                return  params;
            }
        };
        queue.add(request);
    }

    //Metodo para obtener el equipo por su email
    protected void obtenerEquipo(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/equipo/"+email;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Equipo equipo = gson.fromJson(response, Equipo.class);
                        equipo.setPassword(Manager.generateRandomPassword());
                        System.out.println(equipo.getNombre() + " " + equipo.getDescripcion() + " " + equipo.getEmail() + " " + equipo.getPassword());
                        updateEquipo(email, equipo, obtenerToken());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("flx", "ERROR: " + error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Token " + token);
                return  header;
            }
        };
        queue.add(request);
    }

    //Metodo para actualizar el equipo con el nuevo password
    protected void updateEquipo(final String email, final Equipo equipo, final String token){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/equipo/"+email;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        sendEmail(email, equipo.getPassword());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("flx", "ERROR: " + error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Token " + token);
                return  header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("emailEquipo", equipo.getEmail());
                params.put("nombre", equipo.getNombre());
                params.put("descripcion", equipo.getDescripcion());
                params.put("password", equipo.getPassword());
                return  params;
            }
        };
        queue.add(request);

    }


}
