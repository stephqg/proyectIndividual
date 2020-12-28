package pe.pucp.proyectoindividual;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class miMainAdapter extends RecyclerView.Adapter<miMainAdapter.miMainHolder> {

    private ArrayList<claseMain> claseMainArrayList;
    private Context context;
    private static String correo;


    public miMainAdapter(ArrayList<claseMain> claseMainArrayList, Context context, String correo) {
        this.claseMainArrayList = claseMainArrayList;
        this.context = context;
        this.correo = correo;
    }

    public static class miMainHolder extends RecyclerView.ViewHolder{

        private final Context contexto;
        public TextView mitextview;
        public ImageView miImageview;
        String email = miMainAdapter.correo;

        public miMainHolder(@NonNull View itemView) {
            super(itemView);
            contexto = itemView.getContext();
            miImageview = itemView.findViewById(R.id.imageRecyclerMain);
            mitextview = itemView.findViewById(R.id.textViewRecyclerMain);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int posicion = getAdapterPosition();
                    Intent i = new Intent (contexto, DatosActivity.class);
                    String campo = " ";
                    switch (posicion){
                        case 0:
                            campo = "Cafeterias";
                            break;
                        case 1:
                            campo = "Deportes";
                            break;
                        case 2:
                            campo = "Exposiciones y Talleres";
                            break;
                    }

                    i.putExtra("campo", campo);
                    i.putExtra("correo",email);
                    contexto.startActivity(i);
                }
            });
        }
    }

    @NonNull
    @Override
    public miMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main, parent,false);
        miMainHolder mainHolder = new miMainHolder(miView);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull miMainHolder holder, int position) {
        holder.mitextview.setText(claseMainArrayList.get(position).getNombre());

        Glide.with(context)
                .load(claseMainArrayList.get(position).getImg())
                .into(holder.miImageview);
    }


    @Override
    public int getItemCount() {
        return claseMainArrayList.size();
    }
}
