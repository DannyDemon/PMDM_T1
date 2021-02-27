package com.example.api;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorManga extends RecyclerView.Adapter<AdaptadorManga.ViewHolderDatos> implements View.OnClickListener {

    private View.OnClickListener escuchador;
    ArrayList<Manga> miLista;

    public ArrayList<Manga> getMiLista() {
        return miLista;
    }

    public void setMiLista(ArrayList<Manga> miLista) {
        this.miLista = miLista;
    }

    public AdaptadorManga(ArrayList<Manga> miLista) {
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public AdaptadorManga.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_3, null, false);
        laVista.setOnClickListener(this);
        return new ViewHolderDatos(laVista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorManga.ViewHolderDatos holder, int position) {
        holder.asignarDatos(miLista.get(position));
    }

    @Override
    public int getItemCount() {
        if (miLista != null) return miLista.size();
        return 0;
    }

    public void setOnClickListener(View.OnClickListener escucha) {
        this.escuchador = escucha;
    }

    @Override
    public void onClick(View v) {
        if (escuchador != null) escuchador.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView elManga;
        ImageView laImagen;
        TextView elNombre;
        TextView elDes;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            elManga = (TextView) itemView.findViewById(R.id.idManga);
            laImagen = (ImageView) itemView.findViewById(R.id.idFoto);
            elNombre = (TextView) itemView.findViewById(R.id.idNombre);
            elDes = (TextView) itemView.findViewById(R.id.idDes);
            laImagen=(ImageView) itemView.findViewById(R.id.idFoto);
        }

        public void asignarDatos(Manga manga) {
            System.out.println(manga);
            elManga.setText(manga.getId() + "");
            elNombre.setText(manga.getNombre());
            elDes.setText(manga.getDescripcion() +"");

            if(manga.getPath()==0){
                laImagen.setImageResource(R.drawable.im1);

            }else{
                laImagen.setImageResource(manga.getPath());
            }
        }

    }
}

