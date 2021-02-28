package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class ModRol extends AppCompatActivity {
    ArrayList<Usuario> miLista;
    RecyclerView miRecycler;
    EditText edit;
    AdaptadorUsuario2 elAdaptador;
    int modificar;
    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);
    boolean vo = false;
    CheckBox ges;
    CheckBox root;

    private final String Extra_ges = "ges";
    private final String Extra_root = "root";
    private final String Extra_usu = "usu";

    String ges2;
    String root2;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mod_rol);
        ges = findViewById(R.id.checkBox2);
        root = findViewById(R.id.checkBox3);

        Intent intent = getIntent();
        ges2 = intent.getStringExtra(Extra_ges);
        root2 = intent.getStringExtra(Extra_root);
        user = intent.getStringExtra(Extra_usu);

        cbdd.openForWrite();
        miLista = (ArrayList<Usuario>) cbdd.getUsuarios();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        elAdaptador = new AdaptadorUsuario2(miLista);

        miRecycler.setAdapter(elAdaptador);
        listar();
    }

    private void listar() {

        elAdaptador = new AdaptadorUsuario2(miLista);
        //INVOCAR UNA DEVOLUCIÓN DE LLAMADA CUANDO SE HACE CLIC EN UNA VISTA
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarToast(getResources().getString(R.string.pulsado)+ miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre());


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
                int gSi = 0;
                int rSi = 0;
                //SI LAS OCCIONES ESTAN ACTIVADAS PONEN EL ROL A UNO Y SI NO A CERO
                if (ges.isChecked()) {
                    gSi = 1;
                }
                if (root.isChecked()) {
                    rSi = 1;
                }
                usu = new Usuario(usu.getId(), usu.getNombre(),
                        usu.getContra(), usu.getNum()
                        , gSi, rSi);

                //ACTUALIZA EL ROL DE USUARIO SIEMPRE QUENO ESTE ACTIVO
                if (Integer.parseInt(user) != usu.getId()) {

                    int i = cbdd.updateUsuario(usu.getId(), usu);
                    vo = true;
                    if (vo) {
                        vo = false;
                        cargarDatos();
                    }
                    cbdd.ver();
                } else {
                    mostrarToast(getResources().getString(R.string.noRolActivo));
                    cargarDatos();
                }
            }

        });

        miRecycler.setAdapter(elAdaptador);

    }

    //VUELVE A CARGAR ESTA CLASE
    public void cargarDatos() {
        Intent reiniciar = new Intent(ModRol.this, ModRol.class);
        reiniciar.putExtra(Extra_ges, ges2);
        reiniciar.putExtra(Extra_root, root2);
        reiniciar.putExtra(Extra_usu, user);

        ModRol.this.startActivity(reiniciar);
    }

    //VUELVE AL MENU
    public void miVolver(View v) {
        Intent intent = new Intent(v.getContext(), MenuUsuario.class);
        intent.putExtra(Extra_ges, ges2);
        intent.putExtra(Extra_root, root2);
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
