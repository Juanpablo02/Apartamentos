package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileUserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tvCityProfile,tvEmailProfile,tvPhoneProfile,tvRolProfile,tvNameProfile;
    String idUser,rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        tvNameProfile = findViewById(R.id.tvNameProfile);
        tvCityProfile = findViewById(R.id.tvCityProfile);
        tvEmailProfile = findViewById(R.id.tvEmailProfile);
        tvPhoneProfile = findViewById(R.id.tvPhoneProfile);
        tvRolProfile = findViewById(R.id.tvRolProfile);
        idUser = getIntent().getStringExtra("idUser");
        rol = getIntent().getStringExtra("rol");
        readUser();
    }

    public void readUser(){
        DocumentReference docRef = db.collection("Users").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("name");
                        String city = document.getString("city");
                        String email = document.getString("emailContact");
                        String phone = document.getString("phone");
                        String rol = document.getString("rol");

                        tvNameProfile.setText(name);
                        tvCityProfile.setText(city);
                        tvEmailProfile.setText(email);
                        tvPhoneProfile.setText(phone);
                        tvRolProfile.setText(rol);


                    } else {
                        Toast.makeText(ProfileUserActivity.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileUserActivity.this, "La consulta ha fallado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewApart (View view){
        Intent intent = new Intent(this,ApartsUserActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol",rol);
        startActivity(intent);
        finish();
    }

    public void logOut(View view){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        String state = "";
        String id = "";
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor.putString("state", state);
        editor2.putString("id", id);
        editor.commit();
        editor2.commit();
        Toast.makeText(this, "Sesion Finalizada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void viewEditProfile(View view){
        Intent intent = new Intent(this,EditProfileActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol",rol);
        startActivity(intent);
    }
}