package com.example.proyecto.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.Model.Jugador;
import com.example.proyecto.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class JugadorDetailActivity extends AppCompatActivity {

    ImageView jugadorDetailImg;
    TextView jugadorDetailNombre;
    TextView jugadorDetailAlias;
    TextView jugadorDetailEmail;

    //Metode per obtenir el token
    private String obtenerToken(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);
        jugadorDetailImg = findViewById(R.id.jugadorDetailImg);
        jugadorDetailNombre = findViewById(R.id.jugadorDetailNombre);
        jugadorDetailAlias = findViewById(R.id.jugadorDetailAlias);
        jugadorDetailEmail = findViewById(R.id.jugadorDetailEmail);
        final String email = getIntent().getStringExtra("email");
        obtenerJugadorById(obtenerToken(),email);
    }

    //Metode per obtenir un jugador a partir del seu email
    protected void obtenerJugadorById(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugador/"+email;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Jugador jugador = gson.fromJson(response, Jugador.class);
                        jugadorDetailNombre.setText(jugador.getNombre());
                        jugadorDetailAlias.setText(jugador.getAlias());
                        jugadorDetailEmail.setText(jugador.getEmail());
                        if(jugador.getImagen() != null){
                            jugadorDetailImg.setImageBitmap(Manager.StringToBitMap(jugador.getImagen()));
                        }
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
