package br.com.app.business.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.LinkedList;

import br.com.app.utils.Utils;

/**
 * Created by Wesley on 01/10/2016.
 */
public class UsuarioBD extends SQLiteOpenHelper{

    private static final int BD_VERSAO = 2;
    private static final String BD_NOME = "DB_USUARIO";
    private static final String BD_TABELA = "USUARIO";

    private static final String CAMPO_CODIGO = "codigo";
    private static final String CAMPO_NOME = "nome";
    private static final String CAMPO_CARGO = "cargo";
    private static final String CAMPO_EMPRESA = "empresa";
    private static final String CAMPO_FOTO = "foto";

    public UsuarioBD(Context context){
        super(context, BD_NOME, null, BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + BD_TABELA + "(" +
                CAMPO_CODIGO + " INTEGER PRIMARY KEY," +
                CAMPO_NOME + " TEXT," +
                CAMPO_CARGO + " TEXT," +
                CAMPO_EMPRESA + " TEXT," +
                CAMPO_FOTO + " BLOB)";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BD_TABELA);
        onCreate(db);
    }

    public boolean salvar(Usuario usuario, int operacao){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CAMPO_NOME, usuario.getNome());
            values.put(CAMPO_CARGO, usuario.getCargo());
            values.put(CAMPO_EMPRESA, usuario.getEmpresa());
            values.put(CAMPO_FOTO, Utils.getBytes(usuario.getFoto()));

            if(operacao == 1){
                db.update(BD_TABELA, values, CAMPO_CODIGO + " = ?", new String[]{String.valueOf(usuario.getCodigo())});
            } else{
                db.insert(BD_TABELA, null, values);
            }

            db.close();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public LinkedList<Usuario> carregar(int operacao, int codUsuario) {
        try {
            LinkedList<Usuario> listaUsuarios = new LinkedList<Usuario>();

            String sql = "SELECT  * FROM " + BD_TABELA;

            if (operacao == 1) {
                sql += " WHERE CODIGO = " + codUsuario;
            }

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                Usuario objUsuario;
                do {
                    objUsuario = new Usuario();
                    objUsuario.setCodigo(Integer.parseInt(cursor.getString(0)));
                    objUsuario.setNome(cursor.getString(1));
                    objUsuario.setCargo(cursor.getString(2));
                    objUsuario.setEmpresa(cursor.getString(3));
                    objUsuario.setFoto(Utils.getImage(cursor.getBlob(4)));

                    listaUsuarios.add(objUsuario);
                } while (cursor.moveToNext());
            }

            return listaUsuarios;
        } catch (Exception e){
            return null;
        }
    }

    public boolean excluir(Usuario usuario) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BD_TABELA, CAMPO_CODIGO + " = ?", new String[]{String.valueOf(usuario.getCodigo())});
            db.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }
}

