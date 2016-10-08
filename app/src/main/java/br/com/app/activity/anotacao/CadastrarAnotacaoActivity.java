package br.com.app.activity.anotacao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.app.activity.R;
import br.com.app.business.anotacao.AnotacaoBD;
import br.com.app.business.anotacao.AnotacaoDAO;

public class CadastrarAnotacaoActivity extends Activity {

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

    public void salvar(View view){

        EditText txtAnotacao = (EditText) findViewById(R.id.txtAnotacao);
        objAnotacaoDAO.setDescricao(txtAnotacao.getText().toString().trim());

        if(objAnotacaoDAO.getDescricao().isEmpty()){
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Confirmar");
        dialogo.setMessage("Deseja realmente salvar?");
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                salvar();
            }
        });
        dialogo.setNegativeButton("Não", null);
        dialogo.show();
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
