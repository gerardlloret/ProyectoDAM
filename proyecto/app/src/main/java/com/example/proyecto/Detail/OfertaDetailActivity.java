package com.example.proyecto.Detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Model.Equipo;
import com.example.proyecto.Model.Juego;
import com.example.proyecto.Model.Jugador;
import com.example.proyecto.Model.Oferta;
import com.example.proyecto.NavigationController.ControllerActivity;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OfertaDetailActivity extends AppCompatActivity {

    TextView ofertaDetailTitle;
    TextView ofertaDetailDescription;
    TextView ofertaDetailTeam;
    TextView ofertaDetailGame;
    TextView ofertaDetailVacants;
    Button btnOfertaDetailDelete;
    Oferta ofertaSeleccionada;

    //Metodo para obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
    }
    //Metodo para obtener el email
    private String obtenerEmail(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String email = preferences.getString("email", "def");
        System.out.printf(email);
        return email;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_detail);
        ofertaDetailTitle = findViewById(R.id.ofertaDetailTitle);
        ofertaDetailDescription = findViewById(R.id.ofertaDetailDescription);
        ofertaDetailTeam = findViewById(R.id.ofertaDetailTeam);
        ofertaDetailGame = findViewById(R.id.ofertaDetailGame);
        ofertaDetailVacants = findViewById(R.id.ofertaDetailVacants);
        btnOfertaDetailDelete = findViewById(R.id.btnOfertaDetailDelete);

        //Datos pasados de otras pantallas
        final int id = getIntent().getIntExtra("id", 0);
        final int but = getIntent().getIntExtra("but", 0);
        if(but==0){
            btnOfertaDetailDelete.setVisibility(View.GONE);
        }else if(but==1){
            btnOfertaDetailDelete.setText(getResources().getString(R.string.btnDelete));
            btnOfertaDetailDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOferta(obtenerToken(), String.valueOf(id));
                }
            });
        }else if(but==2){
            btnOfertaDetailDelete.setText(getResources().getString(R.string.btnCandidacy));
            btnOfertaDetailDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ofertaSeleccionada.getJugador().add(obtenerEmail());
                    dejarCandidatura(obtenerToken(), String.valueOf(id));
                }
            });
        }
        obtenerOfertaById(obtenerToken(), String.valueOf(id));

    }


    //Metodo para obtener una oferta pasando su id
    protected void obtenerOfertaById(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta/"+id;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Oferta oferta = gson.fromJson(response, Oferta.class);
                        ofertaDetailTitle.setText(oferta.getNombre());
                        ofertaDetailDescription.setText(oferta.getDescripcion());
                        System.out.println(oferta.getEquipo());
                        obtenerEquipoById(obtenerToken(), String.valueOf(oferta.getEquipo()));
                        obtenerJuegoById(obtenerToken(), String.valueOf(oferta.getJuego_id()));
                        ofertaDetailVacants.setText("Vacantes: " + oferta.getVacantes());
                        ofertaSeleccionada = oferta;

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

    //Metodo para obtener un equipo pasando su id
    protected void obtenerEquipoById(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/equipo/"+id;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Equipo equipo = gson.fromJson(response, Equipo.class);
                        ofertaDetailTeam.setText(equipo.getNombre());
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

    //Metodo para obtener un juego pasando su id
    protected void obtenerJuegoById(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/juego/"+id;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Juego juego = gson.fromJson(response, Juego.class);
                        ofertaDetailGame.setText(juego.getNombre());
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

    //Metodo para borrar una oferta
    protected void deleteOferta(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta/"+id;
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
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
        };
        queue.add(request);
    }

    //Metodo para crear la relacion jugador/oferta
    protected void dejarCandidatura(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta/"+id;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        showMessage2();
                        //pushToNavigationController();
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
                params.put("oferta_id", String.valueOf(ofertaSeleccionada.getOferta_id()));
                params.put("nombre", ofertaSeleccionada.getNombre());
                params.put("descripcion", ofertaSeleccionada.getDescripcion());
                params.put("vacantes", String.valueOf(ofertaSeleccionada.getVacantes()));
                params.put("equipo", ofertaSeleccionada.getEquipo());
                params.put("juego", String.valueOf(ofertaSeleccionada.getJuego_id()));
                for (String string : ofertaSeleccionada.getJugador()) {
                    params.put("jugador", string);
                }
                return  params;
            }
        };
        queue.add(request);
    }

    //Metodo para volver al navigation controller
    private void pushToNavigationController(){
        if (!obtenerToken().equalsIgnoreCase("def")) {
            Intent intent = new Intent(OfertaDetailActivity.this, ControllerActivity.class);
            startActivity(intent);
        }
    }

    //Metodo para mostrar un mensaje
    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(OfertaDetailActivity.this).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage(getResources().getString(R.string.offerDelete));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pushToNavigationController();
                    }
                });
        alertDialog.show();
    }

    private void showMessage2(){
        AlertDialog alertDialog = new AlertDialog.Builder(OfertaDetailActivity.this).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage(getResources().getString(R.string.applyCnd));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
