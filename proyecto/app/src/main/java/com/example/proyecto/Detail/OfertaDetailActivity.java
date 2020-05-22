package com.example.proyecto.Detail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Model.Equipo;
import com.example.proyecto.Model.Juego;
import com.example.proyecto.Model.Oferta;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class OfertaDetailActivity extends AppCompatActivity {

    TextView ofertaDetailTitle;
    TextView ofertaDetailDescription;
    TextView ofertaDetailTeam;
    TextView ofertaDetailGame;
    TextView ofertaDetailVacants;

    //Metode per obtenir el token
    private String obtenerToken(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
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
        final int id = getIntent().getIntExtra("id", 0);
        obtenerOfertaById(obtenerToken(), String.valueOf(id));
    }


    //Metode per obtenir una oferta a partir del seu id
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
                        System.out.println(oferta.getEquipo_id());
                        obtenerEquipoById(obtenerToken(), String.valueOf(oferta.getEquipo_id()));
                        obtenerJuegoById(obtenerToken(), String.valueOf(oferta.getJuego_id()));
                        ofertaDetailVacants.setText("Vacantes: " + oferta.getVacantes());
                        //user = detail.getData();
                        //tvPlayerActivity.setText(user.getName());
                        //Picasso.get().load(user.getImage()).into(ivPlayerDetail);
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

}
