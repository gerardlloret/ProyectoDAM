package com.example.proyecto.Edit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.Handler.Manager;
import com.example.proyecto.Model.Equipo;
import com.example.proyecto.Model.Jugador;
import com.example.proyecto.NavigationController.ControllerActivity;
import com.example.proyecto.R;
import com.example.proyecto.Register.TeamRegisterActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.proyecto.Handler.Manager.BitMapToString;

public class PlayerEditActivity extends AppCompatActivity {

    ImageView ivAPEimage;
    ImageButton btnAPEtakePhoto;
    ImageButton btnAPEgalery;
    private static final int imgGalery = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri imageUri;
    EditText etAPEname;
    EditText etAPEalias;
    EditText etAPEcontact;

    //Metodo para obtener el token del shared preferences
    private String obtenerToken() {
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String tok = preferences.getString("token", "def");
        Log.d("gla", tok);
        return tok;
    }

    private String obtenerEmail(){
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String email = preferences.getString("email", "def");
        System.out.printf(email);
        return email;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_edit);

        etAPEname = findViewById(R.id.etAPEname);
        etAPEalias = findViewById(R.id.etAPEalias);
        etAPEcontact = findViewById(R.id.etAPEcontact);
        ivAPEimage = findViewById(R.id.ivAPEimage);
        btnAPEtakePhoto = findViewById(R.id.btnAPEtakePhoto);
        btnAPEgalery = findViewById(R.id.btnAPEgalery);

        obtenerPerfilByEmailRellenarDatos(obtenerToken(), obtenerEmail());

        //Al clicar el botor de fer foto cridarem al metetode dispatchTakePictureIntent
        btnAPEtakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        //Al clicar el botor de galeria cridarem al metetode openGalery
        btnAPEgalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });

        //SETTEAR NUEVOS DATOS
        Button btnAPEdone = findViewById(R.id.btnAPEdone);
        btnAPEdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobaciones()){
                    obtenerPerfilByEmail(obtenerToken(), obtenerEmail());
                }
            }
        });
    }

    //Aquest metode ens enviara a la camara per poder fer una foto
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Aquest metode ens enviara a la galeria per poder seleccionar una foto
    private void openGalery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, imgGalery);
    }

    //Sobreescribim onActivityResult per poder colocar al imageView la foto realitzada per l'usuari o la seleccionada a la galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivAPEimage.setImageBitmap(imageBitmap);
        }
        if(resultCode == RESULT_OK && requestCode == imgGalery){
            imageUri = data.getData();
            ivAPEimage.setImageURI(imageUri);
        }
    }

    //Metode per obtenir un jugador a partir del seu email
    protected void obtenerPerfilByEmail(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
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
                        jugador.setNombre(etAPEname.getText().toString());
                        jugador.setAlias(etAPEalias.getText().toString());
                        jugador.setPassword(etAPEcontact.getText().toString());
                        BitmapDrawable bitmapDrawable = ((BitmapDrawable) ivAPEimage.getDrawable());
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        jugador.setImagen(Manager.BitMapToString(bitmap));

                        updateJugador(email, jugador, obtenerToken());
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


    protected void updateJugador(final String email, final Jugador jugador, final String token){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.66:8000/FreeAgentAPI/v1/jugador/"+email;
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
                        //pushToNavigationController();
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
            params.put("email", jugador.getEmail());
            params.put("nombre", jugador.getNombre());
            params.put("alias", jugador.getAlias());
            params.put("password", jugador.getPassword());
                System.out.println(jugador.getImagen().length());
            params.put("imagen", jugador.getImagen());
            return  params;
            }
        };
        queue.add(request);
    }

    //Metode per obtenir un jugador a partir del seu email
    protected void obtenerPerfilByEmailRellenarDatos(final String token, final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
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
                        if(jugador.getImagen() != null){
                            ivAPEimage.setImageBitmap(Manager.StringToBitMap(jugador.getImagen()));
                        }
                        etAPEname.setText(jugador.getNombre());
                        etAPEalias.setText(jugador.getAlias());
                        etAPEcontact.setText(jugador.getPassword());
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



    public boolean comprobaciones(){
        boolean valido = true;
        if(etAPEname.getText().toString().length()<1||etAPEname.getText().toString().length()>30){
            etAPEname.setError("EL nombre de usuario debe tener de 1 a 30 caracteres");
            valido = false;
        }
        if(etAPEalias.getText().toString().length()<1||etAPEalias.getText().toString().length()>30){
            etAPEalias.setError("EL alias debe tener de 1 a 30 caracteres");
            valido = false;
        }
        if(etAPEcontact.getText().toString().length()<1||etAPEcontact.getText().toString().length()>30){
            etAPEcontact.setError("La contraseña debe tener de 1 a 30 caracteres");
            valido = false;
        }
        return valido;
    }

    private void pushToNavigationController(){
        if (!obtenerToken().equalsIgnoreCase("def")) {
            Intent intent = new Intent(PlayerEditActivity.this, ControllerActivity.class);
            startActivity(intent);
        }
    }

    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(PlayerEditActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Se ha actualizado tu informacion");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pushToNavigationController();
                    }
                });
        alertDialog.show();
    }

}
