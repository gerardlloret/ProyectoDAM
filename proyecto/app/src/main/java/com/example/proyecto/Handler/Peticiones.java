package com.example.proyecto.Handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

import static android.content.Context.MODE_PRIVATE;

public class Peticiones {

    //Metodo para el login, si las credenciales son correctas setea un token en el SharedPreferences
    /*public static void login(final String email, final String pass, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/login/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public Token onResponse(String response) {
                        Gson gson = new Gson();
                        Token token = gson.fromJson(response, Token.class);
                        return token;
                        /*SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
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
                params.put("email", email);
                params.put("password",pass);
                return  params;
            }
        };
        queue.add(request);
    }*/


    //Metodo que comprueba si un jugador existe por su id
    /*public boolean checkJugadorId(final String id, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
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
    }*/

    //Metodo para comprobar si el email de un jugador esta en uso
    /*public boolean checkJugadorEmail(final String email, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
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
                params.put("email", email );
                return params;
            }
        };
        queue.add(request);
        return false;
    }*/

    //Metodo para obtener las ofertas
    /*protected void obtenerOfertas(final String token, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta" + token;
        Log.d("env", url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responses", response);
                        Gson gson = new Gson();
                        RankResponse rankResponse = gson.fromJson(response, RankResponse.class);
                        List<User> users = rankResponse.getUsers();
                        //Collections.sort(users, Collections.reverseOrder());
                        OfertasAdapter adapter = new OfertasAdapter(users);
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("env", "ERROR ENVIAR: " + error.getMessage());
                    }
                }
        );
        queue.add(request);
    }*/

    //Obtener usuario por su id
    /*protected void obtenerUserById(final String token, final String id, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
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
