package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewApartActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String idApart,idUser,rol;
    TextView tvCityView,tvCountryView,tvAddressView,tvValueView,tvRoomsView,tvGoogleMapsView,tvReviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apart);
        idApart = getIntent().getStringExtra("idApart");
        idUser = getIntent().getStringExtra("idUser");
        rol = getIntent().getStringExtra("rol");
        tvCityView = findViewById(R.id.tvCityView);
        tvCountryView = findViewById(R.id.tvCountryView);
        tvAddressView = findViewById(R.id.tvAddressView);
        tvValueView = findViewById(R.id.tvValueView);
        tvRoomsView = findViewById(R.id.tvRoomsView);
        tvGoogleMapsView = findViewById(R.id.tvGoogleMapsView);
        tvReviewView = findViewById(R.id.tvReviewView);
        readApart();
    }

    public void readApart(){
        DocumentReference docRef = db.collection("Aparts").document(idApart);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String countryApart = document.getString("countryApart");
                        String cityApart = document.getString("cityApart");
                        String Address= document.getString("address");
                        String googleMaps= document.getString("googleMaps");
                        String Review= document.getString("review");
                        String Rooms = document.getString("rooms");
                        String Value= document.getString("value");

                        tvCountryView.setText(countryApart);
                        tvCityView.setText(cityApart);
                        tvAddressView.setText(Address);
                        tvGoogleMapsView.setText(googleMaps);
                        tvValueView.setText(Value);
                        tvRoomsView.setText(Rooms);
                        tvReviewView.setText(Review);

                    } else {
                        Toast.makeText(ViewApartActivity.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewApartActivity.this, "La consulta ha fallado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Back(View view){
        Intent intent = new Intent(this,ApartsUserActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol",rol);
        startActivity(intent);
        finish();
    }
}