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
import java.util.Iterator;

public class BorrarUsuario extends AppCompatActivity {
    ArrayList<Usuario> miLista;
    RecyclerView miRecycler;
    AdaptadorUsuario2 elAdaptador;
    int modificar;
    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);
    boolean vo = false;

    //PARAMETROS QUE SE PASAN DESDE EL MENU DE USUARIOS
    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";

    String ges;
    String root;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CONFIGURA LA APLICACION PARA QUE OCUPE TODA LA PANTALLA
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bor_usu);

        //SE GUARDAN LOS PARAMETROS PASADOS DESDE EL MENU A VARIABLES PROPIAS
        Intent intent = getIntent();
        ges = intent.getStringExtra(Extra_ges);
        root = intent.getStringExtra(Extra_root);
        user = intent.getStringExtra(Extra_usu);


        cbdd.openForWrite();
        //PASAMOS LOS DATOS DE LOS USUARIOS AL RECICLER VIEW PARA MOSTRARLOS
        miLista = (ArrayList<Usuario>) cbdd.getUsuarios();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorUsuario2(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();
    }

    private void listar() {

        elAdaptador = new AdaptadorUsuario2(miLista);

        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA.
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SACA MENSAJE CON EL ELEMENTO PULSADO
                Toast.makeText(getApplicationContext(),
                        "Pulsado " + miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre(),
                        Toast.LENGTH_SHORT).show();
                //GUARDA LA POSICION DEL ELEMENTO PULSADO
                modificar = miRecycler.getChildAdapterPosition(v);

                //BUSCA EL USUARIO QUE ESTA EN LA POSICION SELECCIONADA
                ArrayList<Usuario> array = elAdaptador.getMiLista();
                Iterator it = array.iterator();
                Usuario usu = null;
                while (it.hasNext()) {
                    Usuario u = (Usuario) it.next();
                    if (u.getId() == elAdaptador.getMiLista().get(modificar).getId()) {
                        usu = (Usuario) u;
                    }

                }
                //NO PERMITE BORRAR SI ES EL USUARIO ACTIVO
                if (Integer.parseInt(user) != usu.getId()) {
                    //BORRAR EL USUARIO
                    int i = cbdd.removeUsuario(usu.getId());
                    vo = true;
                    if (vo) {
                        vo = false;
                        cargarDatos();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No pueder borrar el usuario activo", Toast.LENGTH_SHORT).show();
                    cargarDatos();
                }

            }
        });

        miRecycler.setAdapter(elAdaptador);

    }

    //CARGA DE NUEVO ESTA ACTIVIDAD
    public void cargarDatos() {
        Intent reiniciar = new Intent(BorrarUsuario.this, BorrarUsuario.class);
        reiniciar.putExtra(Extra_ges, ges);
        reiniciar.putExtra(Extra_root, root);
        reiniciar.putExtra(Extra_usu, user);

        BorrarUsuario.this.startActivity(reiniciar);
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