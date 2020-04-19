package com.example.proyecto.Handler;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Model.Token;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Manager extends AppCompatActivity {

    public boolean checkJugadorId(final String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/checkJugadorId/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
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
                params.put("jugador_id", id );
                return params;
            }
        };
        queue.add(request);
        return false;
    }
}
