package br.com.app.activity.usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.app.activity.R;
import br.com.app.business.usuario.UsuarioBD;
import br.com.app.business.usuario.UsuarioDAO;
import br.com.app.utils.Utils;

public class DadosUsuarioActivity extends Activity {

    private UsuarioDAO objUsuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_usuario);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objUsuarioDAO = new UsuarioDAO(new UsuarioBD(this));

        int codUsuario = 0;

        try {
            codUsuario = Integer.parseInt(getIntent().getExtras().get("COD_USUARIO").toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        carregar(codUsuario);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(int codUsuario){

        objUsuarioDAO.setCodigo(codUsuario);
        if(!objUsuarioDAO.carregarEspecifico()){
            Toast.makeText(this, "Não foi possível carregar os dados do usuário", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        EditText txtNome = (EditText) findViewById(R.id.txtDadosNome);
        txtNome.setText(objUsuarioDAO.getNome());

        EditText txtEmpresa = (EditText) findViewById(R.id.txtDadosEmpresa);
        txtEmpresa.setText(objUsuarioDAO.getEmpresa());

        EditText txtCargo = (EditText) findViewById(R.id.txtDadosCargo);
        txtCargo.setText(objUsuarioDAO.getCargo());

        ImageView imgUsuario = (ImageView) findViewById(R.id.imgUsuario);
        if(objUsuarioDAO.getFoto() == null){
            imgUsuario.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        } else {
            imgUsuario.setImageBitmap(Bitmap.createScaledBitmap(objUsuarioDAO.getFoto(), 400, 350, false));
        }

        txtNome.setEnabled(false);
        txtEmpresa.requestFocus();
    }

    public void salvar(View view){

        EditText txtNome = (EditText) findViewById(R.id.txtDadosNome);
        objUsuarioDAO.setNome(Utils.removerAcento(txtNome.getText().toString()).trim());

        EditText txtCargo = (EditText) findViewById(R.id.txtDadosCargo);
        objUsuarioDAO.setCargo(Utils.removerAcento(txtCargo.getText().toString().trim()));

        EditText txtEmpresa = (EditText) findViewById(R.id.txtDadosEmpresa);
        objUsuarioDAO.setEmpresa(Utils.removerAcento(txtEmpresa.getText().toString().trim()));

        ImageView imgUsuario = (ImageView) findViewById(R.id.imgUsuario);
        objUsuarioDAO.setFoto(((BitmapDrawable)imgUsuario.getDrawable()).getBitmap());

        if(objUsuarioDAO.getNome().isEmpty() || objUsuarioDAO.getCargo().isEmpty() || objUsuarioDAO.getEmpresa().isEmpty()){
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
        if(objUsuarioDAO.editar()){
            Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "Não foi possível salvar", Toast.LENGTH_LONG).show();
        }
    }

    public void excluir(View view) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmar");
        dialogo.setMessage("Deseja realmente excluir?");
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                excluir();
            }
        });
        dialogo.setNegativeButton("Não", null);
        dialogo.show();
    }

    public void excluir(){
        if (!objUsuarioDAO.excluir()){
            Toast.makeText(this, "Não foi possível excluir", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Usuário excluído com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }
}
