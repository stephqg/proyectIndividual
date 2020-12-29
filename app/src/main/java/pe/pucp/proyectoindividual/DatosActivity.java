package pe.pucp.proyectoindividual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatosActivity extends AppCompatActivity {
    String campo;
    String correo;
    TextView textViewTitulo;
    Button botonAgregarMensaje;

    RecyclerView miRecyclerView;
    ArrayList<claseDatos> claseDatosArrayList;
    miDatosAdapter miRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        Intent intent = getIntent();
        campo = intent.getStringExtra("campo");
        correo = intent.getStringExtra("correo");


        textViewTitulo = findViewById(R.id.textViewCategoria);
        textViewTitulo.setText(campo);

        botonAgregarMensaje = findViewById(R.id.buttonAgregarNuevo);
        if(correo.equals("stephanyqg10@hotmail.com")){
            botonAgregarMensaje.setVisibility(View.VISIBLE);
        }

        miRecyclerView = findViewById(R.id.recyclerViewDatos);
        LinearLayoutManager layoutRecyler = new LinearLayoutManager(this);
        miRecyclerView.setLayoutManager(layoutRecyler);
        miRecyclerView.setHasFixedSize(true);

        claseDatosArrayList = new ArrayList<>();
        limpiarArrayList();
        obtenerDatosFirebase(campo, correo);

    }

    public void obtenerDatosFirebase(String campo, String correo){

        FirebaseDatabase.getInstance().getReference().child("Datos").child(campo)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        limpiarArrayList();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            claseDatos miclaseDatos = new claseDatos();
                            miclaseDatos.setTitulo(snapshot1.child("titulo").getValue().toString());
                            miclaseDatos.setLugar(snapshot1.child("lugar").getValue().toString());
                            miclaseDatos.setFecha(snapshot1.child("fecha").getValue().toString());
                            Log.d("infoApp","Está entrando aquí");
                            claseDatosArrayList.add(miclaseDatos);
                        }

                        miRecyclerAdapter = new miDatosAdapter(claseDatosArrayList, getApplicationContext(),correo, campo);
                        miRecyclerView.setAdapter(miRecyclerAdapter);
                        miRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void limpiarArrayList(){
        if (claseDatosArrayList != null){
            claseDatosArrayList.clear();
            if (miRecyclerAdapter != null){
                miRecyclerAdapter.notifyDataSetChanged();
            }
        }
        claseDatosArrayList = new ArrayList<>();

    }

    public void agregarNuevo(View view){

    }

}