package com.example.tp3_pa2_grupo12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ParqueosAdapter extends RecyclerView.Adapter<ParqueosAdapter.ParqueosViewHolder> {

    private Context context;
    private List<Parqueo> parqueoList;

    public ParqueosAdapter(Context context, List<Parqueo> parqueoList) {
        this.context = context;
        this.parqueoList = parqueoList;
    }

    @Override
    public ParqueosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parqueo_item, parent, false);
        return new ParqueosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParqueosViewHolder holder, int position) {
        Parqueo parqueo = parqueoList.get(position);

        holder.textMatricula.setText(parqueo.getNroMatricula());
        holder.textTiempo.setText(parqueo.getTiempo());
    }

    @Override
    public int getItemCount() {
        return parqueoList.size();
    }

    public static class ParqueosViewHolder extends RecyclerView.ViewHolder {
        TextView textMatricula, textTiempo;

        public ParqueosViewHolder(View itemView) {
            super(itemView);
            textMatricula = itemView.findViewById(R.id.textMatricula);
            textTiempo = itemView.findViewById(R.id.textTiempo);
        }
    }
}
