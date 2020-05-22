package com.example.proyecto.NavigationController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Detail.JugadorDetailActivity;
import com.example.proyecto.Model.Jugador;
import com.example.proyecto.Model.Oferta;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragmentTeam extends Fragment {

    RecyclerView homeFragmentTeamRecyclerView;

    public HomeFragmentTeam() {
        // Required empty public constructor
    }

    //Metodo pera obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_team, container, false);

        homeFragmentTeamRecyclerView = view.findViewById(R.id.homeFragmentTeamRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeFragmentTeamRecyclerView.setLayoutManager(layoutManager);
        obtenerJugadores(obtenerToken());
        return view;
    }

    protected void obtenerJugadores(final String token){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugador";
        Log.d("env", url);
        Log.d("env", token);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responses", response);
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<List<Oferta>>(){}.getType();
                        List<Jugador> jugadores = gson.fromJson(response, collectionType);
                        HomeFragmentTeam.JugadoresAdapter adapter = new HomeFragmentTeam.JugadoresAdapter(jugadores);
                        homeFragmentTeamRecyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("env", "ERROR ENVIAR: " + error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Token " + token);
                return  header;
            }
        };
        queue.add(request);
    }

    class JugadoresAdapter extends RecyclerView.Adapter<JugadoresAdapter.ViewHolder> {

        // ViewHolder: Conté referències als diferents objectes del layout
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemPlayerAlias;
            ImageView itemPlayerImg;
            TextView itemPlayerEmail;

            ViewHolder(View view) {
                super(view);
                itemPlayerAlias = view.findViewById(R.id.itemPlayerAlias);
                itemPlayerImg = view.findViewById(R.id.itemPlayerImg);
                itemPlayerEmail = view.findViewById(R.id.itemPlayerEmail);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Jugador jugador = jugadores.get(position);
                        Intent intent = new Intent(getActivity(), JugadorDetailActivity.class);
                        intent.putExtra("email", jugador.getEmail());
                        startActivity(intent);
                    }
                });
            }
        }

        // Dades disponibles gràcies al constructor
        private List<Jugador> jugadores;

        JugadoresAdapter(List<Jugador> jugadores) {
            super();
            this.jugadores = jugadores;
        }

        // Desplegar el layout quan no tenim suficients en pantalla
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("flx", "onCreateViewHolder()");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_player, parent, false);
            return new ViewHolder(view);
        }

        // Associem un element (dades) a un ViewHolder (pantalla)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("flx", "onBindViewHolder() : " + position);
            Jugador jugador = jugadores.get(position);
            //Picasso.get().load(user.getImage()).into(holder.ivRankingPlayer);
            holder.itemPlayerAlias.setText(jugador.getAlias());
            holder.itemPlayerEmail.setText(jugador.getEmail());
        }

        // Indica quants elements tenim a la llista
        @Override
        public int getItemCount() {
            return jugadores.size();
        }

    }

}
