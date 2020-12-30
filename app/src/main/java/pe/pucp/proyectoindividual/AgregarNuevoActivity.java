package pe.pucp.proyectoindividual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AgregarNuevoActivity extends AppCompatActivity {

    EditText etTitulo;
    EditText etFecha;
    EditText etLugar;
    Button btnSubir;
    String campo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nuevo);

        Intent intent = getIntent();
        campo = intent.getStringExtra("campo");

        etTitulo = findViewById(R.id.etTitulo);
        etFecha = findViewById(R.id.etFecha);
        etLugar = findViewById(R.id.etLugar);
        btnSubir = findViewById(R.id.btnSubirPubli);

    }

    public void subirPublicacion(View view){
        claseDatos miClaseDatos = new claseDatos();
        miClaseDatos.setTitulo(etTitulo.getText().toString());
        miClaseDatos.setFecha("Fecha: "+ etFecha.getText().toString());
        miClaseDatos.setLugar("En: "+ etLugar.getText().toString());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Datos").child(campo).push().setValue(miClaseDatos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Publicación agregada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: No se agregó publicación", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}