package br.com.app.activity.horario;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class CadastrarHorarioActivity extends Activity {

    private HorarioDAO objHorarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_horario);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void salvar(View view){

        EditText dtxData = (EditText) findViewById(R.id.txtDadosData);
        objHorarioDAO.setData(FuncoesData.toDate(dtxData.getText().toString(), FuncoesData.DDMMYYYY));

        EditText dtxEntrada = (EditText) findViewById(R.id.txtDadosEntrada);
        objHorarioDAO.setEntrada(FuncoesData.toDate(dtxEntrada.getText().toString().trim(), FuncoesData.HHMM));

        EditText dtxSaida = (EditText) findViewById(R.id.txtDadosSaida);
        objHorarioDAO.setSaida(FuncoesData.toDate(dtxSaida.getText().toString().trim(), FuncoesData.HHMM));


        if(objHorarioDAO.getData() == null || objHorarioDAO.getEntrada() == null || objHorarioDAO.getSaida() == null){
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        if(FuncoesData.comparaDatas(objHorarioDAO.getEntrada(), objHorarioDAO.getSaida()) == -1){
            Toast.makeText(this, "Data de Saída inválida", Toast.LENGTH_LONG).show();
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
        if(objHorarioDAO.salvar()){
            Toast.makeText(this, "Horário cadastrado com sucesso", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, DadosHorarioActivity.class);
            i.putExtra("COD_HORARIO", objHorarioDAO.getCodigo());
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this, "Não foi possível salvar.", Toast.LENGTH_LONG).show();
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), R.style.AppTema, this, year, month, day);
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
