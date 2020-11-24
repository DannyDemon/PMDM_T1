package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerMangas extends AppCompatActivity {
    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";

    String ges;
    String root;
    String user;


    ArrayList<Manga> miLista;
    RecyclerView miRecycler;
    AdaptadorManga elAdaptador;
    int modificar;
    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ver_mangas);

        Intent intent = getIntent();
        ges = intent.getStringExtra(Extra_ges);
        root = intent.getStringExtra(Extra_root);
        user = intent.getStringExtra(Extra_usu);

        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Manga>) cbdd.getMangas();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorManga(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();
    }

    private void listar() {

        AdaptadorManga elAdaptador = new AdaptadorManga(miLista);
        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "Pulsado " + miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre(),
                        Toast.LENGTH_SHORT).show();
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
}