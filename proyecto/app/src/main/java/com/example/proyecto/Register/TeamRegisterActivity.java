package com.example.proyecto.Register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.Model.Token;
import com.example.proyecto.NavigationController.ControllerActivity;
import com.example.proyecto.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class TeamRegisterActivity extends AppCompatActivity {

    EditText ptTeamRegisterUsername;
    EditText ptTeamRegisterEmail;
    EditText ptTeamRegisterPass;
    EditText ptTeamRegisterPass2;
    Button btnTeamRegisterCreate;

    //Metodo para obtener el token del sharedPreferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_register);


        ptTeamRegisterUsername = findViewById(R.id.ptTeamRegisterUsername);
        ptTeamRegisterEmail = findViewById(R.id.ptTeamRegisterEmail);
        ptTeamRegisterPass = findViewById(R.id.ptTeamRegisterPass);
        ptTeamRegisterPass2 = findViewById(R.id.ptTeamRegisterPass2);

        btnTeamRegisterCreate = findViewById(R.id.btnTeamRegisterCreate);
        btnTeamRegisterCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobaciones()) {
                    crearEquipo(ptTeamRegisterUsername.getText().toString(), ptTeamRegisterEmail.getText().toString(), ptTeamRegisterPass.getText().toString());
                }
            }
        });
    }


    //Metodo para crear un equipo
    protected void crearEquipo(final String name, final String email, final String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/signUpEquipo/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Token token = gson.fromJson(response, Token.class);
                        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", token.getToken());
                        editor.putString("email", email);
                        editor.putString("tipo", "equipo");
                        editor.apply();
                        pushToNavigationController();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        ptTeamRegisterEmail.setError(getResources().getString(R.string.vEmailExist));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", name);
                params.put("email", email);
                params.put("password", password);
                params.put("descripcion", "");
                return params;
            }
        };
        queue.add(request);
    }

    //Metodo para validar los datos antes de enviarlos al backend
    public boolean comprobaciones(){
        boolean valido = true;
        if(!ptTeamRegisterPass.getText().toString().equals(ptTeamRegisterPass2.getText().toString())) {
            ptTeamRegisterPass2.setError(getResources().getString(R.string.vPassMatch));
            valido = false;
        }
        if(ptTeamRegisterUsername.getText().toString().length()<1||ptTeamRegisterUsername.getText().toString().length()>30){
            ptTeamRegisterUsername.setError(getResources().getString(R.string.vUsername));
            valido = false;
        }
        if(ptTeamRegisterEmail.getText().toString().length()<1||ptTeamRegisterEmail.getText().toString().length()>30){
            ptTeamRegisterEmail.setError(getResources().getString(R.string.vEmail));
            valido = false;
        }
        if(ptTeamRegisterPass.getText().toString().length()<1||ptTeamRegisterPass.getText().toString().length()>30){
            ptTeamRegisterPass.setError(getResources().getString(R.string.vPass));
            valido = false;
        }
        if(!Manager.emailValido(ptTeamRegisterEmail.getText().toString())){
            ptTeamRegisterEmail.setError(getResources().getString(R.string.vEmailFormat));
            valido = false;
        }
        return valido;
    }

    //Metodo para ir al navigation controller
    private void pushToNavigationController(){
        if (!obtenerToken().equalsIgnoreCase("def")) {
            Intent intent = new Intent(TeamRegisterActivity.this, ControllerActivity.class);
            startActivity(intent);
        }
    }
}