package com.example.api;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.ViewHolderDatos> implements View.OnClickListener {

    private View.OnClickListener escuchador;
    ArrayList<Usuario> miLista;

    public ArrayList<Usuario> getMiLista() {
        return miLista;
    }

    public void setMiLista(ArrayList<Usuario> miLista) {
        this.miLista = miLista;
    }

    public AdaptadorUsuario(ArrayList<Usuario> miLista) {
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public AdaptadorUsuario.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, null, false);
        laVista.setOnClickListener(this);
        return new ViewHolderDatos(laVista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuario.ViewHolderDatos holder, int position) {
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
        TextView elUsuario;
        ImageView laImagen;
        TextView elNombre;
        TextView elTelefono;
        TextView elGes;
        TextView elRoot;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            elUsuario = (TextView) itemView.findViewById(R.id.idUsuario);
            laImagen = (ImageView) itemView.findViewById(R.id.idFoto);
            elNombre = (TextView) itemView.findViewById(R.id.idNombre);
            elTelefono = (TextView) itemView.findViewById(R.id.idTelefono);
            elGes = (TextView) itemView.findViewById(R.id.TextViewg);
            elRoot = (TextView) itemView.findViewById(R.id.TextViewr);
            laImagen=(ImageView) itemView.findViewById(R.id.idFoto);
        }

        public void asignarDatos(Usuario usuario) {
            System.out.println(usuario);
            elUsuario.setText(usuario.getId().toString() + "");
            elNombre.setText(usuario.getNombre());
            elTelefono.setText(usuario.getNum() + "");
            if (usuario.isGes() == 1) {
                elGes.setText("\t\tGestor");
            }
            if (usuario.isRoot() == 1) {
                elRoot.setText("\t\tRoot");
            }

            if(usuario.getPath()==null){
                laImagen.setImageResource(R.drawable.im1);

            }else{
                Drawable d = Drawable.createFromPath(usuario.getPath());
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    laImagen.setBackground(d);
                else
                    laImagen.setBackgroundDrawable(d);
            }
        }

    }
}
