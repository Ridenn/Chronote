package br.com.app.activity.horario;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.adapter.HorariosAdapter;
import br.com.app.business.horario.Horario;
import br.com.app.business.horario.HorarioBD;
import br.com.app.business.horario.HorarioDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

public class HorariosActivity extends Activity {

    private HorarioDAO objHorarioDAO;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        pgbLoading = (ProgressBar)findViewById(R.id.pgbLoading);

        objHorarioDAO = new HorarioDAO(new HorarioBD(this));

        carregar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
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
                final LinkedList<Horario> listaHorarios = objHorarioDAO.carregar();

                hRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listaHorarios == null || listaHorarios.isEmpty()) {
                            Toast.makeText(context, "Horários não encontrados", Toast.LENGTH_LONG).show();
                            if (listaHorarios == null) {
                                pgbLoading.setVisibility(View.INVISIBLE);
                                return;
                            }
                        }

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        adapter = new HorariosAdapter(context, listaHorarios);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);

                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    public void adicionar(View view){
        Utils.chamarActivity(this, EnmTelas.APP_CAD_HORARIO);
    }
}
