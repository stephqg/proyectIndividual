package pe.pucp.proyectoindividual;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        private final Context contexto;


        public miDatosHolder(@NonNull View itemView) {
            super(itemView);
            contexto = itemView.getContext();
            textViewTitulo = itemView.findViewById(R.id.textViewTituloDato);
            textViewLugar = itemView.findViewById(R.id.textViewLugarDato);
            textViewFecha = itemView.findViewById(R.id.textViewFechaDato);
            botonFoto = itemView.findViewById(R.id.buttonFoto);

            botonFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(contexto, Foto.class);
                    i.putExtra("correo", correo);
                    i.putExtra("campo", campo);
                    contexto.startActivity(i);
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
        holder.textViewTitulo.setText(claseDatosArrayList.get(position).getTitulo());
        holder.textViewLugar.setText(claseDatosArrayList.get(position).getLugar());
        holder.textViewFecha.setText(claseDatosArrayList.get(position).getFecha());


    }

    @Override
    public int getItemCount() {
        return claseDatosArrayList.size();
    }
}
