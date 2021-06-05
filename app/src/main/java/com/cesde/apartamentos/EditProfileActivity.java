package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText etCityProfile,etEmailProfile,etPhoneProfile,etNameProfile;
    String idUser,rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        etNameProfile = findViewById(R.id.etNameProfile);
        etCityProfile = findViewById(R.id.etCityProfile);
        etEmailProfile = findViewById(R.id.etEmailProfile);
        etPhoneProfile =  findViewById(R.id.etPhoneProfile);
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

                        etNameProfile.setText(name);
                        etCityProfile.setText(city);
                        etEmailProfile.setText(email);
                        etPhoneProfile.setText(phone);

                    } else {
                        Toast.makeText(EditProfileActivity.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "La consulta ha fallado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void EditUser(View view) {

        if (etNameProfile.length() < 4 || etNameProfile.length() > 30) {
            Toast.makeText(EditProfileActivity.this, "Porfavor digite el nombre correctamente (Entre 4 y 30 caracteres)", Toast.LENGTH_SHORT).show();
        } else if (etCityProfile.length() < 4 || etCityProfile.length() > 30) {
            Toast.makeText(EditProfileActivity.this, "Porfavor digite la ciudad correctamente", Toast.LENGTH_SHORT).show();
        } else if (etEmailProfile.length() > 100 || etEmailProfile.length() < 7) {
            Toast.makeText(EditProfileActivity.this, "Porfavor digite el email correctamente (Menor de 100 caracteres)", Toast.LENGTH_SHORT).show();
        } else if (etPhoneProfile.length() < 7 || etPhoneProfile.length() > 11) {
            Toast.makeText(EditProfileActivity.this, "Porfavor digite su telefono (Entre 7 y 11 digitos)", Toast.LENGTH_SHORT).show();
        } else {

            Map<String, Object> apart = new HashMap<>();

            String name = etNameProfile.getText().toString();
            String city = etCityProfile.getText().toString();
            String email = etEmailProfile.getText().toString();
            String phone = etPhoneProfile.getText().toString();

            apart.put("name", name);
            apart.put("city", city);
            apart.put("phone", phone);
            apart.put("emailContact", email);

            DocumentReference washingtonRef = db.collection("Users").document(idUser);

            washingtonRef
                    .update(apart)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if(rol.equals("Anfitrion")){
                                Toast.makeText(EditProfileActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileActivity.this, ApartsActivity.class);
                                intent.putExtra("idUser",idUser);
                                intent.putExtra("rol",rol);
                                startActivity(intent);
                                finish();
                            } else if(rol.equals("Invitado")){
                                Toast.makeText(EditProfileActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileActivity.this, ApartsUserActivity.class);
                                intent.putExtra("idUser",idUser);
                                intent.putExtra("rol",rol);
                                startActivity(intent);
                                finish();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, "Fallo en la actualización de los datos", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}