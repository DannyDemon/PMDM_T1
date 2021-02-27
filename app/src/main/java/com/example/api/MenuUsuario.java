package com.example.api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuUsuario extends AppCompatActivity {
    // Variables
    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";
    String usur;
    String ges;
    String root;

    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);

    ArrayList<Manga> miLista;
    RecyclerView miRecycler;
    AdaptadorManga elAdaptador;
    int modificar;

    // metodo que carga la pantalla
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu_usuario);
        // enlaze de las botones y el toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Manga>) cbdd.getMangas();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorManga(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();


        //TODOS LOS BOTONES SE OCULTAN
        //     boton1.setVisibility(View.GONE);
        // boton2.setVisibility(View.GONE);
        //  boton3.setVisibility(View.GONE);
        //  boton4.setVisibility(View.GONE);

        // Recojje las variables pasadas de la vista anterior.
        Intent intent = getIntent();

        ges = intent.getStringExtra(Extra_ges);
        root = intent.getStringExtra(Extra_root);
        usur = intent.getStringExtra(Extra_usu);


        //COMPRUEBA LOS ROLES DEL USUARIO ACTIVO Y ACTIVA SUS BOTONES
        if (ges.equals("1")) {
            //boton1.setVisibility(View.VISIBLE);
            //boton3.setVisibility(View.VISIBLE);
        }
        if (Integer.parseInt(root) == 1) {
            // boton1.setVisibility(View.VISIBLE);
            //boton2.setVisibility(View.VISIBLE);
            //boton3.setVisibility(View.VISIBLE);
            //boton4.setVisibility(View.VISIBLE);
        }
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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    //DIFERENTES METODOS DE LOS BOTONES del toolbar
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent llamada;
        switch (id) {

            case R.id.item1:
                // Va a modificar el telefono y asa las propiedades
                 llamada =new Intent(MenuUsuario.this, ModTel.class);
                llamada.putExtra(Extra_ges, ges);
                llamada.putExtra(Extra_root, root);
                llamada.putExtra(Extra_usu, usur);
                startActivity(llamada);
                return true;

            case R.id.item2:
                // Va a borrar usuario y asa las propiedades
                 llamada =new Intent(MenuUsuario.this, BorrarUsuario.class);
                llamada.putExtra(Extra_ges, ges);
                llamada.putExtra(Extra_root, root);
                llamada.putExtra(Extra_usu, usur);
                startActivity(llamada);
                return true;

            case R.id.item3:
                // Va a ver los datos y asa las propiedades
                 llamada =new Intent(MenuUsuario.this, VerDatos.class);
                llamada.putExtra(Extra_ges, ges);
                llamada.putExtra(Extra_root, root);
                llamada.putExtra(Extra_usu, usur);
                startActivity(llamada);
                return true;


            case R.id.item4:

                // Va a ver roles y asa las propiedades
                 llamada =new Intent(MenuUsuario.this, ModRol.class);
                llamada.putExtra(Extra_ges, ges);
                llamada.putExtra(Extra_root, root);
                llamada.putExtra(Extra_usu, usur);
                startActivity(llamada);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
