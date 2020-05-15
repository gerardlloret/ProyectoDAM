package com.example.proyecto.Edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.proyecto.Handler.Manager;
import com.example.proyecto.R;

import java.io.IOException;

import static com.example.proyecto.Handler.Manager.BitMapToString;

public class PlayerEditActivity extends AppCompatActivity {

    ImageView ivAPEimage;
    ImageButton btnAPEtakePhoto;
    ImageButton btnAPEgalery;
    private static final int imgGalery = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_edit);

        ivAPEimage = findViewById(R.id.ivAPEimage);
        btnAPEtakePhoto = findViewById(R.id.btnAPEtakePhoto);
        btnAPEgalery = findViewById(R.id.btnAPEgalery);
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


}
