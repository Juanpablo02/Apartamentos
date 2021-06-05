package com.cesde.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class ApartsUserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreAparts;
    FirestoreRecyclerAdapter adapter;
    String idUser,rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparts_user);
        idUser = getIntent().getStringExtra("idUser");
        rol = getIntent().getStringExtra("rol");
        rvFirestoreAparts = findViewById(R.id.rvFirestoreAparts);

        //query de la base de datos
        Query query = db.collection("Aparts");

        //Configurar opciones del adaptador
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>().
                setQuery(query,UserModel.class).build();

        adapter = new FirestoreRecyclerAdapter<UserModel, ApartsUserActivity.UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ApartsUserActivity.UsersViewHolder holder, int position, @NonNull UserModel model) {
                //Asignar los datos a cada campo
                holder.tvCountry.setText(model.getCountryApart());
                holder.tvCity.setText(model.getCityApart());
                holder.tvAddress.setText(model.getAddress());
                holder.tvRooms.setText(model.getRooms());
                holder.tvValue.setText(model.getValue());
                String id = getSnapshots().getSnapshot(position).getId();

                holder.llGoApart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ApartsUserActivity.this,ViewApartActivity.class);
                        intent.putExtra("idUser",idUser);
                        intent.putExtra("idApart",id);
                        intent.putExtra("rol", rol);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ApartsUserActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //Crear un elemento grafico por cada item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_apart_single_user, parent, false);
                return new ApartsUserActivity.UsersViewHolder(view);
            }
        };
        rvFirestoreAparts.setHasFixedSize(true);
        rvFirestoreAparts.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreAparts.setAdapter(adapter);
    }

    private class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView tvCountry, tvCity, tvAddress, tvRooms, tvValue;
        LinearLayout llGoApart;
        public UsersViewHolder(@NonNull View itemView){
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRooms = itemView.findViewById(R.id.tvRooms);
            tvValue = itemView.findViewById(R.id.tvValue);
            llGoApart = itemView.findViewById(R.id.llGoApart);
        }
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
        Intent intent = new Intent(this,ProfileUserActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("rol", rol);
        startActivity(intent);
        finish();
    }
}