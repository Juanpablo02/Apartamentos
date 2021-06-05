package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.UserModel;

public class ApartsActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreAparts;
    FirestoreRecyclerAdapter adapter;
    String idUser,rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparts);
        idUser = getIntent().getStringExtra("idUser");
        rol = getIntent().getStringExtra("rol");
        rvFirestoreAparts = findViewById(R.id.rvFirestoreAparts);

        //query de la base de datos
        Query query = db.collection("Aparts").whereEqualTo("owner",idUser);

        //Configurar opciones del adaptador
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>().
                setQuery(query,UserModel.class).build();

        adapter = new FirestoreRecyclerAdapter<UserModel,UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserModel model) {
                //Asignar los datos a cada campo
                holder.tvCountry.setText(model.getCountryApart());
                holder.tvCity.setText(model.getCityApart());
                holder.tvAddress.setText(model.getAddress());
                holder.tvRooms.setText(model.getRooms());
                holder.tvValue.setText(model.getValue());
                String id = getSnapshots().getSnapshot(position).getId();

                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        apartDelete(id);
                    }
                });

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ApartsActivity.this,EditApartActivity.class);
                        intent.putExtra("idUser",idUser);
                        intent.putExtra("idApart", id);
                        intent.putExtra("rol", rol);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //Crear un elemento grafico por cada item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_apart_single, parent, false);
                return new UsersViewHolder(view);
            }
        };
        rvFirestoreAparts.setHasFixedSize(true);
        rvFirestoreAparts.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreAparts.setAdapter(adapter);
    }

    private class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView tvCountry, tvCity, tvAddress, tvRooms, tvValue;
        Button btnDelete, btnEdit;
        public UsersViewHolder(@NonNull View itemView){
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRooms = itemView.findViewById(R.id.tvRooms);
            tvValue = itemView.findViewById(R.id.tvValue);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    public void apartDelete(String id){
        db.collection("Aparts").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ApartsActivity.this, "Apartamento borrado correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ApartsActivity.this, "No se pudo borrar el apartamento", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void viewProfile (View view){
        Intent intent = new Intent(this,ProfileActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol", rol);
        startActivity(intent);
        finish();
    }

    public void viewRegisterApart (View view){
        Intent intent = new Intent(this,RegisterApartsActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol", rol);
        startActivity(intent);
        finish();
    }
}