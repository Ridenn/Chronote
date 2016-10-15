package br.com.app.activity.anotacao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.app.activity.R;
import br.com.app.business.anotacao.AnotacaoBD;
import br.com.app.business.anotacao.AnotacaoDAO;

public class CadastrarAnotacaoActivity extends Activity {

    boolean doubleBackToExitPressedOnce = false;
    private AnotacaoDAO objAnotacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anotacao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objAnotacaoDAO = new AnotacaoDAO(new AnotacaoBD(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        salvar(findViewById(R.id.txtAnotacao));
        super.onBackPressed();
        return;
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
        if(objAnotacaoDAO.salvar()){
            Toast.makeText(this, "Anotação cadastrada com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "Não foi possível salvar", Toast.LENGTH_LONG).show();
        }
    }
}
