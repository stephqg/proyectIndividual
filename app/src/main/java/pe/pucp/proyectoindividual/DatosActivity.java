package pe.pucp.proyectoindividual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
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

    //ESTA SECCIÓN ES PARA CREAR Y ACCIONAR EL APP BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAyuda:
                mostrarAyuda();
                return true;
            case R.id.menuLogout:
                logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public void mostrarAyuda(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Ayuda sobre ActualízatePUCP");
        alertDialog.setMessage("Esta aplicación está diseñada para que los usuarios puedan enterarse de las últimas novedades, noticias, eventos, etc. de la PUCP. " +
                "Además, los usuarios podrán tomar fotografías de estos sucesos.");
        alertDialog.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //MENSAJE DE AYUDA MOSTRADO Y ACEPTADO
            }
        });
        alertDialog.show();
    }

    public void logOut(){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(DatosActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    //FIN DE CONFIG DE APP BAR


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
    }

    public void agregarNuevo(View view){
        Intent i = new Intent(this, AgregarNuevoActivity.class);
        i.putExtra("campo", campo);
        startActivity(i);
    }

}