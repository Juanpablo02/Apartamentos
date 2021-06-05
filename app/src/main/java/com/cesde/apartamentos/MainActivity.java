package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText etPasswordLogIn, etEmailLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String state = loadPreferences();
        String id = loadPreferences2();
        String rol = loadPreferences3();

        if(state.equals("active")){
            if(rol.equals("Anfitrion")){
                Intent intent = new Intent(MainActivity.this, ApartsActivity.class);
                intent.putExtra("idUser",id);
                intent.putExtra("rol",rol);
                startActivity(intent);
                finish();
            } else if(rol.equals("Invitado")) {
                Intent intent = new Intent(MainActivity.this, ApartsUserActivity.class);
                intent.putExtra("idUser", id);
                intent.putExtra("rol",rol);
                startActivity(intent);
                finish();
            }
        } else {
            setContentView(R.layout.activity_main);
        }

        etPasswordLogIn = findViewById(R.id.etPasswordLogIn);
        etEmailLogIn = findViewById(R.id.etEmailLogIn);
    }

    public void loginToRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void logIn(View view){

        String email = etEmailLogIn.getText().toString();
        String password = etPasswordLogIn.getText().toString();
        if(etEmailLogIn.length() == 0){
            Toast.makeText(this, "Porfavor digite un email", Toast.LENGTH_SHORT).show();
        } else if(etPasswordLogIn.length() == 0){
            Toast.makeText(this, "Porfavor digite una contraseña", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DocumentReference docRef = db.collection("Users").document(email);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {

                                            String role = document.getString("rol");

                                            if(role.equals("Anfitrion")){
                                                Intent intent = new Intent(MainActivity.this, ApartsActivity.class);
                                                savePreferences(email, role);
                                                intent.putExtra("idUser",email);
                                                intent.putExtra("rol",role);
                                                startActivity(intent);
                                                finish();
                                            } else if(role.equals("Invitado")){
                                                Intent intent = new Intent(MainActivity.this, ApartsUserActivity.class);
                                                savePreferences(email, role);
                                                intent.putExtra("idUser",email);
                                                intent.putExtra("rol",role);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "La consulta ha fallado", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Verifique su Email o Contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    }

    public void savePreferences(String email, String role){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        SharedPreferences preferences3 = getSharedPreferences("rol", Context.MODE_PRIVATE);
        String state = "active";
        String id = email;
        String rol = role;
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferences.Editor editor2 = preferences2.edit();
        SharedPreferences.Editor editor3 = preferences3.edit();
        editor.putString("state", state);
        editor2.putString("id", id);
        editor3.putString("rol", rol);
        editor.commit();
        editor2.commit();
        editor3.commit();
    }

    public String loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String state = preferences.getString("state","error");
        return state;
    }

    public String loadPreferences2(){
        SharedPreferences preferences2 = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        String id = preferences2.getString("id","error");
        return id;
    }

    public String loadPreferences3(){
        SharedPreferences preferences3 = getSharedPreferences("rol", Context.MODE_PRIVATE);
        String rol = preferences3.getString("rol","error");
        return rol;
    }
}