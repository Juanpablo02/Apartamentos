package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText etName, etEmail, etCountry, etCity, etPassword, etConfirmPassword, etPhone;
    CheckBox cbHost, cbInvited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCountry = findViewById(R.id.etCountry);
        etCity = findViewById(R.id.etCity);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        cbHost = findViewById(R.id.cbHost);
        cbInvited = findViewById(R.id.cbInvited);
    }

    public void registerUser(View view) {
        if (etName.length() < 4 || etName.length() > 30) {
            Toast.makeText(RegisterActivity.this, "Porfavor digite el nombre correctamente (Entre 4 y 30 caracteres)", Toast.LENGTH_SHORT).show();
        } else if (etEmail.length() > 100 || etEmail.length() < 7) {
            Toast.makeText(RegisterActivity.this, "Porfavor digite el email correctamente (Menor de 100 caracteres)", Toast.LENGTH_SHORT).show();
        } else if (etPhone.length() < 7 || etPhone.length() > 11) {
            Toast.makeText(RegisterActivity.this, "Porfavor digite su telefono (Entre 7 y 11 digitos)", Toast.LENGTH_SHORT).show();
        } else if (etCountry.length() < 4 || etCountry.length() > 30) {
            Toast.makeText(RegisterActivity.this, "Porfavor digite el País correctamente", Toast.LENGTH_SHORT).show();
        } else if (etCity.length() < 4 || etCity.length() > 30) {
            Toast.makeText(RegisterActivity.this, "Porfavor digite la ciudad correctamente", Toast.LENGTH_SHORT).show();
        } else if (etPassword.length() < 6 || etPassword.length() > 30) {
            Toast.makeText(RegisterActivity.this, "Porfavor digite la contraseña (Entre 6 y 30 caracteres)", Toast.LENGTH_SHORT).show();
        } else if(etConfirmPassword.length() > 0){

            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if(confirmPassword.equals(password)){
                if(cbHost.isChecked() == false && cbInvited.isChecked() == false){
                    Toast.makeText(RegisterActivity.this, "Porfavor seleccione un rol, Invitado o Anfitrion", Toast.LENGTH_SHORT).show();
                } else if(cbHost.isChecked() == true && cbInvited.isChecked() == true){
                    Toast.makeText(RegisterActivity.this, "Porfavor seleccione solo un rol, Invitado o Anfitrion", Toast.LENGTH_SHORT).show();
                } else if(cbHost.isChecked() == true){
                    String rol = "Anfitrion";
                    // Creacion de datos del usuario

                    Map<String, Object> user = new HashMap<>();

                    String name = etName.getText().toString();
                    String email = etEmail.getText().toString();
                    String phone = etPhone.getText().toString();
                    String country = etCountry.getText().toString();
                    String city = etCity.getText().toString();
                    String emailContact = email;

                    user.put("name", name);
                    user.put("email", email);
                    user.put("emailContact", emailContact);
                    user.put("country", country);
                    user.put("city", city);
                    user.put("password", password);
                    user.put("phone", phone);
                    user.put("rol", rol);

                    db.collection("Users").document(email)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Registro Creado Exitosamente", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Registro inconrrecto", Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Creacion de usuario para el Login

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registro Creado Exitosamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registro inconrrecto", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else if (cbInvited.isChecked() == true) {
                    String rol = "Invitado";
                    // Creacion de datos del usuario

                    Map<String, Object> user = new HashMap<>();

                    String name = etName.getText().toString();
                    String email = etEmail.getText().toString();
                    String phone = etPhone.getText().toString();
                    String country = etCountry.getText().toString();
                    String city = etCity.getText().toString();
                    String emailContact = email;

                    user.put("name", name);
                    user.put("email", email);
                    user.put("emailContact", emailContact);
                    user.put("country", country);
                    user.put("city", city);
                    user.put("password", password);
                    user.put("phone", phone);
                    user.put("rol", rol);

                    db.collection("Users").document(email)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Registro Creado Exitosamente", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Registro inconrrecto", Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Creacion de usuario para el Login

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registro Creado Exitosamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registro inconrrecto", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            } else {
                Toast.makeText(RegisterActivity.this, "La confirmacion de contraseña debe coincidir con la contraseña", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "La confirmacion de contraseña debe coincidir con la contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerToLogin(View view){
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}