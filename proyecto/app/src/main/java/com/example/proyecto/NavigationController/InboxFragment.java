package com.example.proyecto.NavigationController;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto.Detail.OfertaDetailActivity;
import com.example.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {

    RecyclerView inboxFragmentRecyclerView;

    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        inboxFragmentRecyclerView = view.findViewById(R.id.inboxFragmentRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        inboxFragmentRecyclerView.setLayoutManager(layoutManager);

        obtenerOfertasJugador("jugador", "token");
        return view;
    }

    protected void obtenerOfertasJugador(final String jugador, final String token){
        /*RequestQueue queue = Volley.newRequestQueue(this);
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
        queue.add(request);*/
        OfertasJugadorAdapter adapter = new OfertasJugadorAdapter();
        inboxFragmentRecyclerView.setAdapter(adapter);
    }

    class OfertasJugadorAdapter extends RecyclerView.Adapter<OfertasJugadorAdapter.ViewHolder> {

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
                        //Oferta oferta = users.get(position);
                        Intent intent = new Intent(getActivity(), OfertaDetailActivity.class);
                        //intent.putExtra("id", oferta.getOferta_id());
                        startActivity(intent);
                    }
                });
            }
        }

        // Dades disponibles gràcies al constructor
        //private List<Oferta> ofertas;

        OfertasJugadorAdapter() {
            super();
            //this.ofertas = ofertas;
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
            //Oferta oferta = oferta.get(position);
            //holder.itemOfertaTitle.setText(oferta.getNombre());
            //holder.tvPuntuation.setText(String.valueOf(user.getTotalScore()));
            //Picasso.get().load(user.getImage()).into(holder.ivRankingPlayer);
            holder.itemOfertaTitle.setText("TITLEINBOX");
            holder.itemOfertaName.setText("NOMBREINBOX");
        }

        // Indica quants elements tenim a la llista
        @Override
        public int getItemCount() {
            //return ofertas.size();
            return 10;
        }

    }

}
