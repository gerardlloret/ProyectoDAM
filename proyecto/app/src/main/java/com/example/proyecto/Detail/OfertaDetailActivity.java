package com.example.proyecto.Detail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class OfertaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_detail);
        //final int id = getIntent().getIntExtra("id", 0);
    }

    //Metode per obtenir el token
    /*private String obtenerToken(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
    }*/

    //Metode per obtenir una oferta a partir del seu id
    /*protected void obtenerUserById(final String token, final String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.flx.cat/dam2game/user/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        //UserResponse detail = gson.fromJson(response, UserResponse.class);
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("token", token );
                params.put("id", id );
                return params;
            }
        };
        queue.add(request);
    }*/

}
