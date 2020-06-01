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

public class InboxFragmentTeam extends Fragment {

    RecyclerView inboxFragmentTeamRecyclerView;

    public InboxFragmentTeam() {
        // Required empty public constructor
    }

    //Metodo para obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        System.out.printf(tok);
        return tok;
    }
    //Metodo para obtener el email
    private String obtenerEmail(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String email = preferences.getString("email", "def");
        System.out.printf(email);
        return email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox_team, container, false);
        inboxFragmentTeamRecyclerView = view.findViewById(R.id.inboxFragmentTeamRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        inboxFragmentTeamRecyclerView.setLayoutManager(layoutManager);

        obtenerOfertasEquipo(obtenerEmail(), obtenerToken());
        return view;
    }

    //Metodo para obtener las ofertas de un equipo
    protected void obtenerOfertasEquipo(final String email, final String token){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/ofertaByEquipo/"+email;
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
                        List<Oferta> ofertas = gson.fromJson(response, collectionType);
                        InboxFragmentTeam.OfertasEquipoAdapter adapter = new InboxFragmentTeam.OfertasEquipoAdapter(ofertas);
                        inboxFragmentTeamRecyclerView.setAdapter(adapter);
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

    class OfertasEquipoAdapter extends RecyclerView.Adapter<OfertasEquipoAdapter.ViewHolder> {

        //ViewHolder: Contiene las referencia al layout
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
                        intent.putExtra("but",1);
                        startActivity(intent);
                    }
                });
            }
        }

        private List<Oferta> ofertas;

        OfertasEquipoAdapter(List<Oferta> ofertas) {
            super();
            this.ofertas = ofertas;
        }

        //Desplegar el layout
        @NonNull
        @Override
        public InboxFragmentTeam.OfertasEquipoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("flx", "onCreateViewHolder()");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_oferta, parent, false);
            return new InboxFragmentTeam.OfertasEquipoAdapter.ViewHolder(view);
        }

        //Mostramos los datos
        @Override
        public void onBindViewHolder(@NonNull InboxFragmentTeam.OfertasEquipoAdapter.ViewHolder holder, int position) {
            Log.d("flx", "onBindViewHolder() : " + position);
            Oferta oferta = ofertas.get(position);
            holder.itemOfertaTitle.setText(oferta.getNombre());
            holder.itemOfertaName.setText(oferta.getDescripcion());
        }

        //Indica cuantos elementos tenemos
        @Override
        public int getItemCount() {
            return ofertas.size();
        }

    }
}
