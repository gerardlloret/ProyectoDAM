package com.example.proyecto.Edit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.proyecto.NavigationController.ControllerActivity;
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

    //Metodo para obtener el email del shared preferences
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

    //Metodo para obtener un equipo a partir de su id
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

    //Metodo para rellenar los datos de la pantalla
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

    //Metodo para actualizar la informacion del equipo
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
                        showMessage();
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

    //Validacion de datos antes de enviarlos al backend
    public boolean comprobaciones(){
        boolean valido = true;
        if(etATEname.getText().toString().length()<1||etATEname.getText().toString().length()>30){
            etATEname.setError(getResources().getString(R.string.vUsername));
            valido = false;
        }
        if(etATEdescription.getText().toString().length()>100){
            etATEdescription.setError(getResources().getString(R.string.vDescription));
            valido = false;
        }
        if(etATEpass.getText().toString().length()<1||etATEpass.getText().toString().length()>30){
            etATEpass.setError(getResources().getString(R.string.vUsername));
            valido = false;
        }
        return valido;
    }

    //Metodo para volver al Navigation controller
    private void pushToNavigationController(){
        if (!obtenerToken().equalsIgnoreCase("def")) {
            Intent intent = new Intent(TeamEditActivity.this, ControllerActivity.class);
            startActivity(intent);
        }
    }

    //Metodo para mostrar un mensaje
    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(TeamEditActivity.this).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage(getResources().getString(R.string.editProfileApply));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pushToNavigationController();
                    }
                });
        alertDialog.show();
    }
}
