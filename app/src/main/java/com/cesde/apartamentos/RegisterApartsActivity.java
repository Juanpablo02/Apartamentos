package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterApartsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText etCountryApart,etCityApart,etAddress,etGoogleMaps,etValue,etRooms,etImages,etFeatured,etReview;
    String idUser,rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_aparts);
        idUser = getIntent().getStringExtra("idUser");
        rol = getIntent().getStringExtra("rol");
        etCountryApart = findViewById(R.id.etCountryApart);
        etCityApart = findViewById(R.id.etCityApart);
        etAddress = findViewById(R.id.etAddress);
        etGoogleMaps = findViewById(R.id.etGoogleMaps);
        etValue = findViewById(R.id.etValue);
        etRooms = findViewById(R.id.etRooms);
        etImages = findViewById(R.id.etImages);
        etFeatured = findViewById(R.id.etFeatured);
        etReview = findViewById(R.id.etReview);
    }

    public void apartRegister(View view){

        if(etCityApart.length()<2 || etCityApart.length()>30){
            Toast.makeText(this, "Porfavor digite la ciudad", Toast.LENGTH_SHORT).show();
        } else if(etCountryApart.length()<4 || etCountryApart.length()>30){
            Toast.makeText(this, "Porfavor digite el país", Toast.LENGTH_SHORT).show();
        }  else if(etAddress.length()<4 || etAddress.length()>40){
            Toast.makeText(this, "Porfavor digite la dirección", Toast.LENGTH_SHORT).show();
        } else if(etGoogleMaps.length()<4 || etGoogleMaps.length()>100){
            Toast.makeText(this, "Porfavor pegue el link de Google Maps", Toast.LENGTH_SHORT).show();
        }  else if(etRooms.length()<1 || etRooms.length()>5){
            Toast.makeText(this, "Porfavor digite las habitaciones del apartamento", Toast.LENGTH_SHORT).show();
        } else if(etValue.length()<3 || etValue.length()>30){
            Toast.makeText(this, "Porfavor digite el valor del apartamento", Toast.LENGTH_SHORT).show();
        } else if(etImages.length()<4 || etImages.length()>30){
            Toast.makeText(this, "Porfavor inserte las imagenes del apartamento", Toast.LENGTH_SHORT).show();
        } else if(etFeatured.length()<4 || etFeatured.length()>30){
            Toast.makeText(this, "Porfavor inserte la imagen mas destacada", Toast.LENGTH_SHORT).show();
        } else if(etReview.length()<4 || etReview.length()>150){
            Toast.makeText(this, "Porfavor digite una breve reseña del apartamento", Toast.LENGTH_SHORT).show();
        } else {

            //Creacion de datos del apartamento

            Map<String, Object> apart = new HashMap<>();

            String cityApart = etCityApart.getText().toString();
            String countryApart = etCountryApart.getText().toString();
            String address = etAddress.getText().toString();
            String googleMaps = etGoogleMaps.getText().toString();
            String rooms = etRooms.getText().toString();
            String value = etValue.getText().toString();
            String images = etImages.getText().toString();
            String featured = etFeatured.getText().toString();
            String review = etReview.getText().toString();
            String owner = idUser;

            apart.put("cityApart", cityApart);
            apart.put("countryApart", countryApart);
            apart.put("address", address);
            apart.put("googleMaps", googleMaps);
            apart.put("rooms", rooms);
            apart.put("value", value);
            apart.put("images", images);
            apart.put("featured", featured);
            apart.put("review", review);
            apart.put("owner", owner);

            db.collection("Aparts").document(address)
                    .set(apart)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterApartsActivity.this, "Apartamento Añadido Exitosamente", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterApartsActivity.this, "Error al añadir apartamento", Toast.LENGTH_SHORT).show();
                        }
                    });
            Intent intent = new Intent(this,ApartsActivity.class);
            intent.putExtra("idUser",idUser);
            intent.putExtra("rol",rol);
            startActivity(intent);
            finish();
        }
    }

    public void viewApart (View view){
        Intent intent = new Intent(this,ApartsActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol",rol);
        startActivity(intent);
        finish();
    }

    public void viewProfile (View view){
        Intent intent = new Intent(this,ProfileActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol",rol);
        startActivity(intent);
        finish();
    }
}