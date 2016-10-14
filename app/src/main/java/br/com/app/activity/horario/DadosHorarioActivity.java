package br.com.app.activity.horario;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.com.app.activity.R;
import br.com.app.business.horario.HorarioBD;
import br.com.app.business.horario.HorarioDAO;
import br.com.app.utils.FuncoesData;
import br.com.app.utils.Utils;

public class DadosHorarioActivity extends Activity {

    boolean doubleBackToExitPressedOnce = false;
    private HorarioDAO objHorarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_horario);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objHorarioDAO = new HorarioDAO(new HorarioBD(this));

        EditText dtxData = (EditText) findViewById(R.id.txtDadosData);
        dtxData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        EditText dtxEntrada = (EditText) findViewById(R.id.txtDadosEntrada);
        dtxEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        EditText dtxSaida = (EditText) findViewById(R.id.txtDadosSaida);
        dtxSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment2();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        int codHorario = 0;

        try {
            codHorario = Integer.parseInt(getIntent().getExtras().get("COD_HORARIO").toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        carregar(codHorario);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

//    @Override
//    public void onBackPressed(){
//        salvar(findViewById(R.id.txtDadosData));
//        return;
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            salvar(findViewById(R.id.txtDadosData));
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Clique 2 vezes para sair", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void carregar(int codUsuario){

        objHorarioDAO.setCodigo(codUsuario);
        if(!objHorarioDAO.carregarEspecifico()){
            Toast.makeText(this, "Não foi possível carregar os dados do horário", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        EditText dtxData = (EditText) findViewById(R.id.txtDadosData);
        dtxData.setText(FuncoesData.formatDate(objHorarioDAO.getData(), FuncoesData.DDMMYYYY));

        EditText dtxEntrada = (EditText) findViewById(R.id.txtDadosEntrada);
        dtxEntrada.setText(FuncoesData.formatDate(objHorarioDAO.getEntrada(), FuncoesData.HHMM));

        EditText dtxSaida = (EditText) findViewById(R.id.txtDadosSaida);
        dtxSaida.setText(FuncoesData.formatDate(objHorarioDAO.getSaida(), FuncoesData.HHMM));

        dtxData.setEnabled(false);
        dtxEntrada.requestFocus();
    }

    public void salvar(View view){

        EditText dtxData = (EditText) findViewById(R.id.txtDadosData);
        objHorarioDAO.setData(FuncoesData.toDate(dtxData.getText().toString(), FuncoesData.DDMMYYYY));

        EditText dtxEntrada = (EditText) findViewById(R.id.txtDadosEntrada);
        objHorarioDAO.setEntrada(FuncoesData.toDate(dtxEntrada.getText().toString().trim(), FuncoesData.HHMM));

        EditText dtxSaida = (EditText) findViewById(R.id.txtDadosSaida);
        objHorarioDAO.setSaida(FuncoesData.toDate(dtxSaida.getText().toString().trim(), FuncoesData.HHMM));


        if(objHorarioDAO.getData() == null || objHorarioDAO.getEntrada() == null || objHorarioDAO.getSaida() == null){
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(FuncoesData.comparaDatas(objHorarioDAO.getEntrada(), objHorarioDAO.getSaida()) == 1){
            Toast.makeText(this, "Data de Saída inválida", Toast.LENGTH_SHORT).show();
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
        if(objHorarioDAO.editar()){
            Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Não foi possível salvar", Toast.LENGTH_SHORT).show();
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
        if (!objHorarioDAO.excluir()){
            Toast.makeText(this, "Não foi possível excluir", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Horário excluído com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText dtxData = (EditText) getActivity().findViewById(R.id.txtDadosData);
            dtxData.setText(Utils.preencher(day, "0", 2) + "/" + Utils.preencher(month, "0", 2) + "/" + year);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), R.style.AppTema, this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            EditText dtxEntrada = (EditText) getActivity().findViewById(R.id.txtDadosEntrada);
            dtxEntrada.setText(Utils.preencher(hourOfDay, "0", 2) + ":" + Utils.preencher(minute, "0", 2));
        }
    }

    public static class TimePickerFragment2 extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), R.style.AppTema, this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            EditText dtxEntrada = (EditText) getActivity().findViewById(R.id.txtDadosSaida);
            dtxEntrada.setText(Utils.preencher(hourOfDay, "0", 2) + ":" + Utils.preencher(minute, "0", 2));
        }
    }
}
