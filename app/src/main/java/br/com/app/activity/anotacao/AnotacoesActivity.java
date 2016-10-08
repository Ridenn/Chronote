package br.com.app.activity.anotacao;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.LinkedList;

import br.com.app.activity.R;
import br.com.app.adapter.AnotacoesAdapter;
import br.com.app.business.anotacao.Anotacao;
import br.com.app.business.anotacao.AnotacaoBD;
import br.com.app.business.anotacao.AnotacaoDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

public class AnotacoesActivity extends Activity {

    private AnotacaoDAO objAnotacaoDAO;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);

        objAnotacaoDAO = new AnotacaoDAO(new AnotacaoBD(this));

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

    public void carregar() {

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);
        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Context context = this;
        final Handler hRecycleView = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinkedList<Anotacao> listaAnotacoes = objAnotacaoDAO.carregar();

                hRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listaAnotacoes == null || listaAnotacoes.isEmpty()) {
                            Toast.makeText(context, "Anotações não encontradas", Toast.LENGTH_LONG).show();
                            if (listaAnotacoes == null) {
                                pgbLoading.setVisibility(View.INVISIBLE);
                                return;
                            }
                        }

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setVisibility(View.INVISIBLE);

                        adapter = new AnotacoesAdapter(context, listaAnotacoes);

                        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);

                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    public void adicionar(View view){
        Utils.chamarActivity(this, EnmTelas.APP_CAD_ANOTACAO);
    }
}