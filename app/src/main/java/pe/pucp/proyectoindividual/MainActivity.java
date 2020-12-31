package pe.pucp.proyectoindividual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String correo;
    String uid;
    String nombreUsuario;
    TextView mitextview;

    RecyclerView miRecyclerView;
    ArrayList<claseMain> claseMainArrayList;
    miMainAdapter miRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        correo = intent.getStringExtra("email");
        uid = intent.getStringExtra("UID");
        nombreUsuario = intent.getStringExtra("nombreUsuario");

        mitextview = findViewById(R.id.text1);
        mitextview.setText("Hola "+ nombreUsuario);
        miRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutRecyler = new LinearLayoutManager(this);
        miRecyclerView.setLayoutManager(layoutRecyler);
        miRecyclerView.setHasFixedSize(true);

        claseMainArrayList = new ArrayList<>();
        limpiarArrayList();
        obtenerImagenesFirebase();

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

            }
        });
        alertDialog.show();
    }

    public void logOut(){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    //ESTA SECCIÓN ES PARA CREAR Y ACCIONAR EL APP BAR


    public void obtenerImagenesFirebase(){

        FirebaseDatabase.getInstance().getReference().child("Main")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        limpiarArrayList();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            claseMain miclaseMain = new claseMain();
                            miclaseMain.setNombre(snapshot1.child("nombre").getValue().toString());
                            miclaseMain.setImg(snapshot1.child("img").getValue().toString());
                            Log.d("infoApp","Está entrando aquí");
                            claseMainArrayList.add(miclaseMain);
                        }

                        miRecyclerAdapter = new miMainAdapter(claseMainArrayList, getApplicationContext(),correo);
                        miRecyclerView.setAdapter(miRecyclerAdapter);
                        miRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void limpiarArrayList(){
        if (claseMainArrayList != null){
            claseMainArrayList.clear();
            if (miRecyclerAdapter != null){
                miRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

}