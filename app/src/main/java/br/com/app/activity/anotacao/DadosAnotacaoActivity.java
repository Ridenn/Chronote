package br.com.app.activity.anotacao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.app.activity.R;
import br.com.app.business.anotacao.AnotacaoBD;
import br.com.app.business.anotacao.AnotacaoDAO;
import br.com.app.utils.Utils;

public class DadosAnotacaoActivity extends Activity {

    private EditText txtTitulo;
    private EditText txtAnotacao;
    private AnotacaoDAO objAnotacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_anotacao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objAnotacaoDAO = new AnotacaoDAO(new AnotacaoBD(this));

        int codAnotacao = 0;

        try {
            codAnotacao = Integer.parseInt(getIntent().getExtras().get("COD_ANOTACAO").toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        carregar(codAnotacao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anotacoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itmCompartilhar:
                Utils.compartilharAnotacao(findViewById(R.id.txtAnotacao), txtTitulo.getText().toString());
                break;
            case R.id.itmDelete:
                excluir(null);
                break;
            default:
                onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

            salvar(findViewById(R.id.txtAnotacao));
            super.onBackPressed();
            return;

//                if(objAnotacaoDAO.getDescricao().isEmpty()){
//                    excluir();
//                    carregar(objAnotacaoDAO.getCodigo());
//                    return;
//                }
    }

    public void carregar(int codUsuario){

        objAnotacaoDAO.setCodigo(codUsuario);
        if(!objAnotacaoDAO.carregarEspecifico()){
            Toast.makeText(this, "Não foi possível carregar o detalhe da anotação.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtTitulo.setText(objAnotacaoDAO.getTitulo());

        txtAnotacao = (EditText) findViewById(R.id.txtAnotacao);
        txtAnotacao.setText(objAnotacaoDAO.getDescricao());
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void salvar(View view){

        EditText txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        objAnotacaoDAO.setTitulo(txtTitulo.getText().toString().trim());

        EditText txtAnotacao = (EditText) findViewById(R.id.txtAnotacao);
        objAnotacaoDAO.setDescricao(txtAnotacao.getText().toString().trim());

        if(objAnotacaoDAO.getTitulo().isEmpty()){
            objAnotacaoDAO.setTitulo(getDateTime());
        }

        if(objAnotacaoDAO.getDescricao().isEmpty()){
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        salvar();
//        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
//
//        dialogo.setTitle(Html.fromHtml("<font color='#000000'>Confirmar</font>"));
//        dialogo.setMessage(Html.fromHtml("<font color='#000000'>Deseja realmente salvar?</font>"));
//        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                salvar();
//            }
//        });
//        dialogo.setNegativeButton("Não", null);
//        dialogo.show();
    }

    public void salvar(){
        if(objAnotacaoDAO.editar()){
            Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "Não foi possível salvar", Toast.LENGTH_LONG).show();
        }
    }

    public void excluir(View view) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(Html.fromHtml("<font color='#000000'>Confirmar</font>"));
        dialogo.setMessage(Html.fromHtml("<font color='#000000'>Deseja realmente excluir?</font>"));
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                excluir();
            }
        });
        dialogo.setNegativeButton("Não", null);
        dialogo.show();
    }

    public void excluir(){
        if (!objAnotacaoDAO.excluir()){
            Toast.makeText(this, "Não foi possível excluir", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Anotação excluída com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }
}
