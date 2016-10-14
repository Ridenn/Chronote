package br.com.app.activity.usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import br.com.app.activity.R;
import br.com.app.business.usuario.UsuarioBD;
import br.com.app.business.usuario.UsuarioDAO;
import br.com.app.utils.Utils;

public class CadastrarUsuarioActivity extends Activity {

    private UsuarioDAO objUsuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objUsuarioDAO = new UsuarioDAO(new UsuarioBD(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void salvar(View view){

        EditText txtNome = (EditText) findViewById(R.id.txtNomeCadUsuario);
        objUsuarioDAO.setNome(Utils.removerAcento(txtNome.getText().toString()).trim());

        EditText txtCargo = (EditText) findViewById(R.id.txtCargoCadUsuario);
        objUsuarioDAO.setCargo(Utils.removerAcento(txtCargo.getText().toString().trim()));

        EditText txtEmpresa = (EditText) findViewById(R.id.txtEmpresaCadUsuario);
        objUsuarioDAO.setEmpresa(Utils.removerAcento(txtEmpresa.getText().toString().trim()));

        ImageView imgUsuario = (ImageView) findViewById(R.id.imgUsuario);
        objUsuarioDAO.setFoto(((BitmapDrawable)imgUsuario.getDrawable()).getBitmap());

        if(objUsuarioDAO.getNome().isEmpty() || objUsuarioDAO.getCargo().isEmpty() || objUsuarioDAO.getEmpresa().isEmpty()){
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle(Html.fromHtml("<font color='#000000'>Confirmar</font>"));
        dialogo.setMessage(Html.fromHtml("<font color='#000000'>Deseja realmente salvar?</font>"));
        
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                salvar();
            }
        });
        dialogo.setNegativeButton("Não", null);
        dialogo.show();
    }

    public void salvar(){
        if(objUsuarioDAO.salvar()){
            Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "Não foi possível salvar.", Toast.LENGTH_LONG).show();
        }
    }

    public void alterarImagem(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                bmp = Bitmap.createScaledBitmap(bmp, 400, 400, false);
                inputStream.close();
                ImageView imgUsuario = (ImageView) findViewById(R.id.imgUsuario);
                imgUsuario.setImageBitmap(bmp);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
