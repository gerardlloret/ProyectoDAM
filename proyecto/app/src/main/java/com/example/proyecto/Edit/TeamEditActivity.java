package com.example.proyecto.Edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.Model.Equipo;
import com.example.proyecto.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class TeamEditActivity extends AppCompatActivity {

    EditText etATEname;
    EditText etATEpass;
    EditText etATEdescription;

    //Metodo para obtener el token del shared preferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }

    private String obtenerEmail(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String email = preferences.getString("email", "def");
        System.out.printf(email);
        return email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        etATEname = findViewById(R.id.etATEname);
        etATEpass= findViewById(R.id.etATEpass);
        etATEdescription = findViewById(R.id.etATEdescription);

        obtenerPerfilByEmailRellenarDatos(obtenerToken(), obtenerEmail());

        //SETTEAR NUEVOS DATOS
        Button btnATEdone = findViewById(R.id.btnATEdone);
        btnATEdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobaciones()){
                    obtenerPerfilByEmail(obtenerToken(), obtenerEmail());
                }
            }
        });
    }

    //Metode per obtenir un jugador a partir del seu email
    protected void obtenerPerfilByEmail(final String token, final String email){
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
                        equipo.setNombre(etATEname.getText().toString());
                        equipo.setDescripcion(etATEdescription.getText().toString());
                        equipo.setPassword(etATEpass.getText().toString());
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

    //Metode per obtenir un jugador a partir del seu email
    protected void obtenerPerfilByEmailRellenarDatos(final String token, final String email){
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
                        etATEname.setText(equipo.getNombre());
                        etATEdescription.setText(equipo.getDescripcion());
                        etATEpass.setText(equipo.getPassword());
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


    public boolean comprobaciones(){
        boolean valido = true;
        if(etATEname.getText().toString().length()<1||etATEname.getText().toString().length()>30){
            etATEname.setError("EL nombre de usuario debe tener de 1 a 30 caracteres");
            valido = false;
        }
        if(etATEdescription.getText().toString().length()>100){
            etATEdescription.setError("La descripcion no debe tener mas de 100");
            valido = false;
        }
        if(etATEpass.getText().toString().length()<1||etATEpass.getText().toString().length()>30){
            etATEpass.setError("La contraseña debe tener de 1 a 30 caracteres");
            valido = false;
        }
        return valido;
    }
}
