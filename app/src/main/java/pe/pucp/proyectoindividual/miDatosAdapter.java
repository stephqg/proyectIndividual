package pe.pucp.proyectoindividual;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class miDatosAdapter extends RecyclerView.Adapter<miDatosAdapter.miDatosHolder>{

    private ArrayList<claseDatos> claseDatosArrayList;
    private Context context;
    private static String correo;
    private static String campo;

    public miDatosAdapter(ArrayList<claseDatos> claseDatosArrayList, Context context, String correo, String campo) {
        this.claseDatosArrayList = claseDatosArrayList;
        this.context = context;
        this.correo = correo;
        this.campo = campo;
    }

    public static class miDatosHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitulo;
        public TextView textViewLugar;
        public TextView textViewFecha;
        public Button botonFoto;
        public Button botonBorrar;
        private final Context contexto;
        public claseDatos miClaseDatos;


        public miDatosHolder(@NonNull View itemView) {
            super(itemView);
            contexto = itemView.getContext();
            textViewTitulo = itemView.findViewById(R.id.textViewTituloDato);
            textViewLugar = itemView.findViewById(R.id.textViewLugarDato);
            textViewFecha = itemView.findViewById(R.id.textViewFechaDato);
            botonFoto = itemView.findViewById(R.id.buttonFoto);
            botonBorrar = itemView.findViewById(R.id.buttonBorrar);

            if (correo.equals("stephanyqg10@hotmail.com")){
                botonBorrar.setVisibility(View.VISIBLE);
            }

            botonFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(contexto, Foto.class);
                    i.putExtra("correo", correo);
                    i.putExtra("campo", campo);
                    contexto.startActivity(i);
                }
            });

            botonBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(contexto);
                    alertDialog.setTitle("Eliminar Elemento");
                    alertDialog.setTitle("¿Seguro que desea eliminar publicación?");
                    alertDialog.setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("Datos").child(campo).orderByChild("titulo").equalTo(miClaseDatos.getTitulo())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                Toast.makeText(contexto,"Borrado exitoso",Toast.LENGTH_SHORT).show();
                                                String key = dataSnapshot.getKey();
                                                FirebaseDatabase.getInstance().getReference().child("Datos").child(campo).child(key).removeValue();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                        }
                    });
                    alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Borrado Cancelado
                        }
                    });
                    alertDialog.show();
                }
            });

        }
    }



    @NonNull
    @Override
    public miDatosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_datos,parent,false);
        miDatosHolder datosHolder = new miDatosHolder(itemview);
        return datosHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull miDatosHolder holder, int position) {
        claseDatos miClaseDatos = claseDatosArrayList.get(position);
        holder.miClaseDatos = miClaseDatos;
        holder.textViewTitulo.setText(claseDatosArrayList.get(position).getTitulo());
        holder.textViewLugar.setText(claseDatosArrayList.get(position).getLugar());
        holder.textViewFecha.setText(claseDatosArrayList.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return claseDatosArrayList.size();
    }
}
