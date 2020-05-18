package com.example.proyecto.Register;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.Handler.Manager;
import com.example.proyecto.R;

public class TeamRegisterActivity extends AppCompatActivity {

    EditText ptTeamRegisterUsername;
    EditText ptTeamRegisterEmail;
    EditText ptTeamRegisterPass;
    EditText ptTeamRegisterPass2;
    Button btnTeamRegisterCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_register);


        ptTeamRegisterUsername = findViewById(R.id.ptTeamRegisterUsername);
        ptTeamRegisterEmail = findViewById(R.id.ptTeamRegisterEmail);
        ptTeamRegisterPass = findViewById(R.id.ptTeamRegisterPass);
        ptTeamRegisterPass2 = findViewById(R.id.ptTeamRegisterPass2);

        btnTeamRegisterCreate = findViewById(R.id.btnTeamRegisterCreate);
    }

    //Metodo para obtener el token del shared preferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }

    public boolean comprobaciones(){
        boolean valido = true;
        if(!ptTeamRegisterPass.getText().toString().equals(ptTeamRegisterPass2.getText().toString())) {
            ptTeamRegisterPass2.setError("Las constraseñas deben coincidir");
            valido = false;
        }
        if(ptTeamRegisterUsername.getText().toString().length()<1||ptTeamRegisterUsername.getText().toString().length()>30){
            ptTeamRegisterUsername.setError("EL nombre de usuario debe tener de 1 a 30 caracteres");
            valido = false;
        }
        if(ptTeamRegisterEmail.getText().toString().length()<1||ptTeamRegisterEmail.getText().toString().length()>30){
            ptTeamRegisterEmail.setError("EL email debe tener de 1 a 30 caracteres");
            valido = false;
        }
        if(ptTeamRegisterPass.getText().toString().length()<1||ptTeamRegisterPass.getText().toString().length()>30){
            ptTeamRegisterPass.setError("La contraseña debe tener de 1 a 30 caracteres");
            valido = false;
        }
        if(!Manager.emailValido(ptTeamRegisterEmail.getText().toString())){
            ptTeamRegisterEmail.setError("Este email no tiene un formato valido");
            valido = false;
        }
        return valido;
    }
}