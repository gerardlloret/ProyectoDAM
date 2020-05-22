package com.example.proyecto.NavigationController;


import android.content.Intent;
import android.content.SharedPreferences;
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
public class InboxFragment extends Fragment {

    RecyclerView inboxFragmentRecyclerView;

    public InboxFragment() {
        // Required empty public constructor
    }

    //Metodo pera obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        System.out.printf(tok);
        return tok;
    }

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
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        inboxFragmentRecyclerView = view.findViewById(R.id.inboxFragmentRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        inboxFragmentRecyclerView.setLayoutManager(layoutManager);

        obtenerOfertasJugador(obtenerEmail(), obtenerToken());
        return view;
    }

    protected void obtenerOfertasJugador(final String email, final String token){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta";
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
                        for (int i = ofertas.size()-1; i >= 0; i--) {
                            boolean contains = false;
                            Oferta o = ofertas.get(i);
                            for (int j = 0; j < o.getJugador().size(); j++) {
                                if (o.getJugador().get(j).equalsIgnoreCase(email)){
                                    contains = true;
                                    System.out.println(contains);
                                }
                            }
                            if (!contains) {
                                ofertas.remove(o);
                            }
                        }
                        InboxFragment.OfertasJugadorAdapter adapter = new InboxFragment.OfertasJugadorAdapter(ofertas);
                        inboxFragmentRecyclerView.setAdapter(adapter);
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

        OfertasJugadorAdapter(List<Oferta> ofertas) {
            super();
            this.ofertas = ofertas;
        }

        // Desplegar el layout quan no tenim suficients en pantalla
        @NonNull
        @Override
        public InboxFragment.OfertasJugadorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("flx", "onCreateViewHolder()");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_oferta, parent, false);
            return new InboxFragment.OfertasJugadorAdapter.ViewHolder(view);
        }

        // Associem un element (dades) a un ViewHolder (pantalla)
        @Override
        public void onBindViewHolder(@NonNull InboxFragment.OfertasJugadorAdapter.ViewHolder holder, int position) {
            Log.d("flx", "onBindViewHolder() : " + position);
            Oferta oferta = ofertas.get(position);
            holder.itemOfertaTitle.setText(oferta.getNombre());
            holder.itemOfertaName.setText(oferta.getDescripcion());
        }

        // Indica quants elements tenim a la llista
        @Override
        public int getItemCount() {
            return ofertas.size();
        }

    }

}
