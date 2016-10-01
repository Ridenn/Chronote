package br.com.app.business.horario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

import br.com.app.utils.FuncoesData;

/**
 * Created by Wesley on 01/10/2016.
 */
public class HorarioBD extends SQLiteOpenHelper{

    private static final int BD_VERSAO = 3;
    private static final String BD_NOME = "DB_HORARIO";
    private static final String BD_TABELA = "HORARIO";

    private static final String CAMPO_CODIGO = "codigo";
    private static final String CAMPO_USUARIO = "usuario";
    private static final String CAMPO_DATA = "data";
    private static final String CAMPO_ENTRADA = "entrada";
    private static final String CAMPO_SAIDA = "saida";

    public HorarioBD(Context context){
        super(context, BD_NOME, null, BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + BD_TABELA + "(" +
                CAMPO_CODIGO + " INTEGER PRIMARY KEY," +
                CAMPO_USUARIO + " INTEGER," +
                CAMPO_DATA + " DATETIME," +
                CAMPO_ENTRADA + " DATETIME," +
                CAMPO_SAIDA + " DATETIME)";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BD_TABELA);
        onCreate(db);
    }

    public boolean salvar(Horario horario, int operacao){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CAMPO_DATA, FuncoesData.formatDate(horario.getData(), FuncoesData.DDMMYYYY));
            values.put(CAMPO_ENTRADA, FuncoesData.formatDate(horario.getEntrada(), FuncoesData.HHMM));
            values.put(CAMPO_SAIDA, FuncoesData.formatDate(horario.getSaida(), FuncoesData.HHMM));
            values.put(CAMPO_USUARIO, String.valueOf(horario.getUsuario()));

            if(operacao == 1){
                db.update(BD_TABELA, values, CAMPO_CODIGO + " = ? AND " + CAMPO_USUARIO + " = ?", new String[]{String.valueOf(horario.getCodigo()), String.valueOf(horario.getUsuario())});
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

    public LinkedList<Horario> carregar(int operacao, int codHorario, int codUsuario) {
        try {
            LinkedList<Horario> listaHorarios = new LinkedList<Horario>();

            String sql = "SELECT  * FROM " + BD_TABELA + " WHERE USUARIO = " + codUsuario;

            if (operacao == 1) {
                sql += " AND CODIGO = " + codHorario;
            }

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                Horario objHorario;
                do {
                    objHorario = new Horario();
                    objHorario.setCodigo(Integer.parseInt(cursor.getString(0)));
                    objHorario.setUsuario(cursor.getInt(1));
                    objHorario.setData(FuncoesData.toDate(cursor.getString(2), FuncoesData.DDMMYYYY));
                    objHorario.setEntrada(FuncoesData.toDate(cursor.getString(3), FuncoesData.HHMM));
                    objHorario.setSaida(FuncoesData.toDate(cursor.getString(4), FuncoesData.HHMM));

                    listaHorarios.add(objHorario);
                } while (cursor.moveToNext());
            }

            return listaHorarios;
        } catch (Exception e){
            return null;
        }
    }

    public boolean excluir(Horario horario) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BD_TABELA, CAMPO_CODIGO + " = ? AND " + CAMPO_USUARIO + " = ?", new String[]{String.valueOf(horario.getCodigo()), String.valueOf(horario.getUsuario())});
            db.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }
}

