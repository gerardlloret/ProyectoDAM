package com.example.proyecto.NavigationController;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Edit.PlayerEditActivity;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Jugador;
import com.example.proyecto.Model.Oferta;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView userProfileName;
    TextView userProfileAlias;
    TextView userProfileEmail;
    ImageView userProfileImage;

    public ProfileFragment() {
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
        SharedPreferences preferences = getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", "def");
        editor.apply();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);


        userProfileName = view.findViewById(R.id.userProfileName);
        userProfileAlias = view.findViewById(R.id.userProfileAlias);
        userProfileEmail = view.findViewById(R.id.userProfileEmail);
        userProfileImage = view.findViewById(R.id.userProfileImage);

        //Push to edit
        Button btnFHeditProfile = view.findViewById(R.id.btnFHeditProfile);
        btnFHeditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayerEditActivity.class);
                startActivity(intent);
            }
        });


        //Boton LogOut
        Button btnFHPlogOut = view.findViewById(R.id.btnFHPlogOut);
        btnFHPlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        obtenerPerfilByEmail(obtenerToken(), obtenerEmail());
        return view;
    }

    //Metodo para obtener un jugador a partir de su email
    protected void obtenerPerfilByEmail(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugador/"+email;
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Jugador jugador = gson.fromJson(response, Jugador.class);
                        userProfileName.setText(jugador.getNombre());
                        userProfileAlias.setText(jugador.getAlias());
                        userProfileEmail.setText(jugador.getEmail());
                        if(jugador.getImagen() != null){
                            userProfileImage.setImageBitmap(Manager.StringToBitMap(jugador.getImagen()));
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
