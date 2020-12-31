package pe.pucp.proyectoindividual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Foto extends AppCompatActivity {

    String campo;
    String correo;
    ImageView imageViewFoto;
    EditText editTextnombre;
    Bitmap imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        Intent intent = getIntent();
        campo = intent.getStringExtra("campo");
        correo = intent.getStringExtra("correo");

        imageViewFoto = findViewById(R.id.imageView);
        editTextnombre = findViewById(R.id.etNombreFoto);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void tomarFoto(View view) {

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);
        }else{
            permisoFoto();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisoFoto();
            } else {
                Toast.makeText(this, "No hay permisos de cámara", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void permisoFoto(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            imagen = (Bitmap) data.getExtras().get("data");
            imageViewFoto.setImageBitmap(imagen);
        }
    }

    public void subirFotoFirebase(View view){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        ByteArrayOutputStream fotoJPG = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG, 100, fotoJPG);
        byte cadenabytes[] = fotoJPG.toByteArray();

        String nombreArchivo = correo + "-" + editTextnombre.getText().toString();
        StorageReference referenciaFinal = storageRef.child(campo + "/" + nombreArchivo);

        referenciaFinal.putBytes(cadenabytes)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Foto subida exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

}