package br.com.app.business.anotacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

/**
 * Created by Lucas on 08/10/2016.
 */
public class AnotacaoBD extends SQLiteOpenHelper {

    private static final int BD_VERSAO = 2;
    private static final String BD_NOME = "DB_ANOTACAO";
    private static final String BD_TABELA = "ANOTACAO";

    private static final String CAMPO_CODIGO = "codigo";
    private static final String CAMPO_USUARIO = "usuario";
    private static final String CAMPO_TITULO = "titulo";
    private static final String CAMPO_DESCRICAO = "descricao";

    public AnotacaoBD(Context context){
        super(context, BD_NOME, null, BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + BD_TABELA + "(" +
                CAMPO_CODIGO + " INTEGER PRIMARY KEY," +
                CAMPO_USUARIO + " INTEGER," +
                CAMPO_TITULO + " TEXT," +
                CAMPO_DESCRICAO + " TEXT)";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BD_TABELA);
        onCreate(db);
    }

    public boolean salvar(Anotacao anotacao, int operacao){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CAMPO_USUARIO, String.valueOf(anotacao.getUsuario()));
            values.put(CAMPO_TITULO, anotacao.getTitulo());
            values.put(CAMPO_DESCRICAO, anotacao.getDescricao());

            if(operacao == 1){
                db.update(BD_TABELA, values, CAMPO_CODIGO + " = ? AND " + CAMPO_USUARIO + " = ?", new String[]{String.valueOf(anotacao.getCodigo()), String.valueOf(anotacao.getUsuario())});
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

    public LinkedList<Anotacao> carregar(int operacao, int codAnotacao, int codUsuario) {
        try {
            LinkedList<Anotacao> listaHorarios = new LinkedList<Anotacao>();

            String sql = "SELECT  * FROM " + BD_TABELA + " WHERE USUARIO = " + codUsuario;

            if (operacao == 1) {
                sql += " AND CODIGO = " + codAnotacao;
            }

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                Anotacao objAnotacao;
                do {
                    objAnotacao = new Anotacao();
                    objAnotacao.setCodigo(Integer.parseInt(cursor.getString(0)));
                    objAnotacao.setUsuario(cursor.getInt(1));
                    objAnotacao.setTitulo(cursor.getString(2));
                    objAnotacao.setDescricao(cursor.getString(3));

                    listaHorarios.add(objAnotacao);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            }
            return listaHorarios;
        } catch (Exception e){
            return null;
        }
    }

    public boolean excluir(Anotacao anotacao) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BD_TABELA, CAMPO_CODIGO + " = ? AND " + CAMPO_USUARIO + " = ?", new String[]{String.valueOf(anotacao.getCodigo()), String.valueOf(anotacao.getUsuario())});
            db.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
