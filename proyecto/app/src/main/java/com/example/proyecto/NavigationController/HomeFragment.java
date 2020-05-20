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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Detail.OfertaDetailActivity;
import com.example.proyecto.Model.Oferta;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //AppCompatTextView fragHomeTest;
    RecyclerView homeFragmentRecyclerView;

    public HomeFragment() {
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
        Log.d("gla", "HOME: ");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeFragmentRecyclerView = view.findViewById(R.id.homeFragmentRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeFragmentRecyclerView.setLayoutManager(layoutManager);
        //return inflater.inflate(R.layout.fragment_home, container, false);
        obtenerOfertas(obtenerToken());
        return view;
    }

   protected void obtenerOfertas(final String token){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta";
        Log.d("env", url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responses", response);
                        Gson gson = new Gson();
                        //OfertaResponse ofertaResponse = gson.fromJson(response, OfertaResponse.class);
                        //List<Oferta> ofertas = ofertaResponse.getOfertas();
                        Type collectionType = new TypeToken<List<Oferta>>(){}.getType();
                        List<Oferta> ofertas = gson.fromJson(response, collectionType);
                        OfertasAdapter adapter = new OfertasAdapter(ofertas);
                        homeFragmentRecyclerView.setAdapter(adapter);
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
        //OfertasAdapter adapter = new OfertasAdapter();
        //homeFragmentRecyclerView.setAdapter(adapter);
    }


    class OfertasAdapter extends RecyclerView.Adapter<OfertasAdapter.ViewHolder> {

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
                        Oferta oferta = ofertas.get(position);
                        Intent intent = new Intent(getActivity(), OfertaDetailActivity.class);
                        intent.putExtra("id", oferta.getOferta_id());
                        startActivity(intent);
                    }
                });
            }
        }

        // Dades disponibles gràcies al constructor
        private List<Oferta> ofertas;

        OfertasAdapter(List<Oferta> ofertas) {
            super();
            this.ofertas = ofertas;
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
            Oferta oferta = ofertas.get(position);
            //holder.itemOfertaTitle.setText(oferta.getNombre());
            //holder.tvPuntuation.setText(String.valueOf(user.getTotalScore()));
            //Picasso.get().load(user.getImage()).into(holder.ivRankingPlayer);
            holder.itemOfertaTitle.setText(oferta.getNombre());
            holder.itemOfertaName.setText(oferta.getDescripcion());
        }

        // Indica quants elements tenim a la llista
        @Override
        public int getItemCount() {
            return ofertas.size();
            //return 10;
        }

    }



}
