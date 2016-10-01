package br.com.app.activity.usuario;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.adapter.UsuariosAdapter;
import br.com.app.business.usuario.Usuario;
import br.com.app.business.usuario.UsuarioBD;
import br.com.app.business.usuario.UsuarioDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

public class UsuarioActivity extends Activity {

    private UsuarioDAO objUsuarioDAO;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        pgbLoading = (ProgressBar)findViewById(R.id.pgbLoading);

        objUsuarioDAO = new UsuarioDAO(new UsuarioBD(this));

        carregar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_principal, menu);

        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0);
            item.setTitle(spanString);
        }

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmSobre:
                Utils.chamarActivity(this, EnmTelas.APP_SOBRE);
                break;
            default:
        }

        return true;
    }

    public void carregar(){

        pgbLoading = (ProgressBar)findViewById(R.id.pgbLoading);
        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Context context = this;
        final Handler hRecycleView = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinkedList<Usuario> listaUsuarios = objUsuarioDAO.carregar();

                hRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
                            Toast.makeText(context, "Usuários não encontrados", Toast.LENGTH_LONG).show();
                            if (listaUsuarios == null) {
                                pgbLoading.setVisibility(View.INVISIBLE);
                                return;
                            }
                        }

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        adapter = new UsuariosAdapter(context, listaUsuarios);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);

                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    public void adicionar(View view){
        Utils.chamarActivity(this, EnmTelas.APP_CAD_USUARIO);
    }
}
