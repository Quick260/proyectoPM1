package adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.daytoday.Info_completa;
import com.example.daytoday.R;

import java.util.ArrayList;

import Global.arraylist;
import Pojo.archivo;

public class AdaptadorGaleria extends RecyclerView.Adapter<AdaptadorGaleria.GaleriaHolder> {

    public AdaptadorGaleria(ArrayList<archivo> archivosDatos) {

    }

    @NonNull
    @Override
    public AdaptadorGaleria.GaleriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_galeria, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new GaleriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorGaleria.GaleriaHolder holder, int position) {

        final int pos = position;
        holder.nom.setText(arraylist.archivosDatos.get(position).getNomObra());
        String imageUrl = arraylist.archivosDatos.get(position).getPath();
        Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paseSeeDates = new Intent(view.getContext(), Info_completa.class);
                paseSeeDates.putExtra("pos", pos);
                view.getContext().startActivity(paseSeeDates);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.archivosDatos.size();
    }

    public class GaleriaHolder extends RecyclerView.ViewHolder {
        TextView nom;
        ImageView imageView;
        public GaleriaHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.txt_recy);
            imageView = itemView.findViewById(R.id.imagen_recy);
        }
    }
}
