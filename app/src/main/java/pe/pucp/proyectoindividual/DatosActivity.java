package pe.pucp.proyectoindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DatosActivity extends AppCompatActivity {
    String campo;
    String correo;
    TextView textViewTitulo;
    TextView textViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        Intent intent = getIntent();
        campo = intent.getStringExtra("campo");
        correo = intent.getStringExtra("correo");
        textViewTitulo = findViewById(R.id.textViewCategoria);
        textViewTitulo.setText(campo);
        textViewX = findViewById(R.id.textView3);
        textViewX.setText(correo);

    }
}