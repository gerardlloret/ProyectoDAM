package com.example.proyecto.NavigationController;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Detail.OfertaDetailActivity;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    AppCompatTextView fragHomeTest;
    RecyclerView homeFragmentRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    //Metodo pera obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("gla", "HOME: ");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fragHomeTest = view.findViewById(R.id.fragHomeTest);
        fragHomeTest.setText("Funciona");
        homeFragmentRecyclerView = view.findViewById(R.id.homeFragmentRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeFragmentRecyclerView.setLayoutManager(layoutManager);
        //return inflater.inflate(R.layout.fragment_home, container, false);
        obtenerOfertas("");
        return view;
    }

    protected void obtenerOfertas(final String token){
        /*RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.flx.cat/dam2game/ranking?token=" + token;
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
                        Collections.sort(users, Collections.reverseOrder());
                        RankingAdapter adapter = new RankingAdapter(users);
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
        queue.add(request);*/
        RankingAdapter adapter = new RankingAdapter();
        homeFragmentRecyclerView.setAdapter(adapter);
    }


    class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

        // ViewHolder: Conté referències als diferents objectes del layout
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemOfertaTitle;
            ImageView itemOfertaImg;
            TextView itemOfertaName;

            ViewHolder(View view) {
                super(view);
                itemOfertaTitle = view.findViewById(R.id.itemOfertaTitle);
                itemOfertaImg = view.findViewById(R.id.itemOfertaImg);
                itemOfertaName = view.findViewById(R.id.itemOfertaName);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        //User user = users.get(position);
                        Intent intent = new Intent(getActivity(), OfertaDetailActivity.class);
                        //intent.putExtra("id", user.getId());
                        startActivity(intent);
                    }
                });
            }
        }

        // Dades disponibles gràcies al constructor
        //private List<User> users;

        RankingAdapter() {
            super();
            //this.users = users;
        }

        // Desplegar el layout quan no tenim suficients en pantalla
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("flx", "onCreateViewHolder()");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_oferta, parent, false);
            return new ViewHolder(view);
        }

        // Associem un element (dades) a un ViewHolder (pantalla)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("flx", "onBindViewHolder() : " + position);
            //User user = users.get(position);
            //holder.tvPlayerName.setText(user.getName());
            //holder.tvPuntuation.setText(String.valueOf(user.getTotalScore()));
            //Picasso.get().load(user.getImage()).into(holder.ivRankingPlayer);
            holder.itemOfertaTitle.setText("TITLE");
            holder.itemOfertaName.setText("NOMBRE");
        }

        // Indica quants elements tenim a la llista
        @Override
        public int getItemCount() {
            //return users.size();
            return 10;
        }

    }



}
