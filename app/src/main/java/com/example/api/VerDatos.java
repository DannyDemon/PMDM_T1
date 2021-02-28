package com.example.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VerDatos extends AppCompatActivity {
    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";

    String ges;
    String root;
    String user;


    ArrayList<Usuario> miLista;
    RecyclerView miRecycler;
    AdaptadorUsuario elAdaptador;
    int modificar;
    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ver_datos);

        Intent intent = getIntent();
        ges = intent.getStringExtra(Extra_ges);
        root = intent.getStringExtra(Extra_root);
        user = intent.getStringExtra(Extra_usu);

        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Usuario>) cbdd.getUsuarios();
        System.out.println(miLista);
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorUsuario(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();
    }

    private void listar() {

        AdaptadorUsuario elAdaptador = new AdaptadorUsuario(miLista);
        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarToast(getResources().getString(R.string.pulsado)+ miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre());

                modificar = miRecycler.getChildAdapterPosition(v);


            }
        });

        miRecycler.setAdapter(elAdaptador);

    }

    //VUELVE AL MENU
    public void miVolver(View v) {
        Intent intent = new Intent(v.getContext(), MenuUsuario.class);
        intent.putExtra(Extra_ges, ges);
        intent.putExtra(Extra_root, root);
        intent.putExtra(Extra_usu, user);

        startActivity(intent);
    }

    private void mostrarToast(String texto){
        LayoutInflater inflater = getLayoutInflater();
        View layout =inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.layout_base));

        TextView textView =layout.findViewById(R.id.txt);
        textView.setText(texto);

        Toast toast=new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}