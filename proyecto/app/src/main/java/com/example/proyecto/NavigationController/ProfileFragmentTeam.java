package com.example.proyecto.NavigationController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Edit.TeamEditActivity;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Equipo;
import com.example.proyecto.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragmentTeam extends Fragment {

    TextView tvFPTnombre;
    TextView tvFPTemail;
    TextView tvFPTvacantes;
    TextView tvFPTmiembros;
    TextView tvFPTdescripcion;


    public ProfileFragmentTeam() {
        // Required empty public constructor
    }

    //Metode para obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        return tok;
    }
    //Metode para obtener el email
    private String obtenerEmail(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String email = preferences.getString("email", "def");
        System.out.printf(email);
        return email;
    }
    //Metodo para LogOut, borramos el token del sharedPrederences
    private void logOut(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", "def");
        editor.apply();
        System.out.println(obtenerToken() + " LOLAAAA");
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_team, container, false);

        tvFPTnombre = view.findViewById(R.id.tvFPTnombre);
        tvFPTemail = view.findViewById(R.id.tvFPTemail);
        tvFPTvacantes = view.findViewById(R.id.tvFPTvacantes);
        tvFPTmiembros = view.findViewById(R.id.tvFPTmiembros);
        tvFPTdescripcion = view.findViewById(R.id.tvFPTdescripcion);

        obtenerPerfilByEmail(obtenerToken(), obtenerEmail());

        //Push to edit
        Button btnFHeditProfileTeam = view.findViewById(R.id.btnFHeditProfileTeam);
        btnFHeditProfileTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeamEditActivity.class);
                startActivity(intent);
            }
        });

        //Boton LogOut
        Button btnFPTlogOut = view.findViewById(R.id.btnFPTlogOut);
        btnFPTlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        return view;
    }


    //Metodo para obtener un equipo a partir de su email
    protected void obtenerPerfilByEmail(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/equipo/"+email;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Equipo equipo = gson.fromJson(response, Equipo.class);
                        tvFPTnombre.setText(equipo.getNombre());
                        tvFPTemail.setText(equipo.getEmail());
                        tvFPTvacantes.setText(String.valueOf(equipo.getVacantes()));
                        tvFPTmiembros.setText(String.valueOf(equipo.getNumero_miembros()));
                        if (null == equipo.getDescripcion() || equipo.getDescripcion().equalsIgnoreCase("")){
                            tvFPTdescripcion.setText(getResources().getString(R.string.notDesc));
                        } else {
                            tvFPTdescripcion.setText(equipo.getDescripcion());
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
