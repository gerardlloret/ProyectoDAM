package com.example.proyecto.NavigationController;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Detail.OfertaDetailActivity;
import com.example.proyecto.Model.Juego;
import com.example.proyecto.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CreateOfertaFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText etFCOname;
    EditText etFCOdescription;
    EditText etFCOvacancies;
    Spinner spFCOgames;
    HashMap<String, String> hmJuegos = new HashMap<String, String>();
    ArrayList<String> spJuegos = new ArrayList<>();
    String idJuegoSeleccionado;

    public CreateOfertaFragment() {
        // Required empty public constructor
    }

    //Metodo pera obtener el token
    private String obtenerToken(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        etFCOname = view.findViewById(R.id.etFCOname);
        etFCOdescription = view.findViewById(R.id.etFCOdescription);
        etFCOvacancies = view.findViewById(R.id.etFCOvacancies);

        obtenerJuegos(obtenerToken());

        spFCOgames = view.findViewById(R.id.spFCOgames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, spJuegos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFCOgames.setAdapter(adapter);
        spFCOgames.setOnItemSelectedListener(this);

        Button btnFCOcreate = view.findViewById(R.id.btnFCOcreate);
        btnFCOcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearOferta(obtenerToken());
            }
        });
        return view;
    }


    protected void obtenerJuegos(final String token){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/juego/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<List<Juego>>(){}.getType();
                        List<Juego> juegos = gson.fromJson(response, collectionType);
                        for(Juego j : juegos){
                            hmJuegos.put(j.getNombre(), String.valueOf(j.getJuego_id()));
                            spJuegos.add(j.getNombre());
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


    protected void crearOferta(final String token){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/oferta/";
        System.out.println(url);
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("flx", response);
                        Gson gson = new Gson();
                        showMessage();
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

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", etFCOname.getText().toString());
                params.put("descripcion", etFCOdescription.getText().toString());
                params.put("vacantes", etFCOvacancies.getText().toString());
                params.put("equipo", obtenerEmail());
                params.put("juego", idJuegoSeleccionado);
                return  params;
            }
        };
        queue.add(request);
    }

    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage("Se ha creado la oferta satisfactoriamente");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    //Metodos del spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String gameSelect = parent.getItemAtPosition(position).toString();
        idJuegoSeleccionado = hmJuegos.get(gameSelect);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
