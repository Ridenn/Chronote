package br.com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.app.activity.R;
import br.com.app.activity.horario.DadosHorarioActivity;
import br.com.app.business.horario.Horario;
import br.com.app.utils.FuncoesData;

/**
 * Created by Wesley on 10/09/2016.
 */
public class HorariosAdapter extends RecyclerView.Adapter<HorariosAdapter.ViewHolder> {

    private Context context;
    private static List<Horario> listaHorarios;

    public HorariosAdapter(Context context, List<Horario> listaHorarios) {
        this.context = context;
        this.listaHorarios = listaHorarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_horarios, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Horario objHorario = listaHorarios.get(position);

        viewHolder.lblData.setText(FuncoesData.formatDate(objHorario.getData(), FuncoesData.DDMMYYYY));
        viewHolder.lblEntrada.setText("Entrada: " + FuncoesData.formatDate(objHorario.getEntrada(), FuncoesData.HHMM));
        viewHolder.lblSaida.setText("Saída: " + FuncoesData.formatDate(objHorario.getSaida(), FuncoesData.HHMM));

        viewHolder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return listaHorarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int posicao;

        public TextView lblData;
        public TextView lblEntrada;
        public TextView lblSaida;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            lblData = (TextView) itemLayoutView.findViewById(R.id.lblData);
            lblEntrada = (TextView) itemLayoutView.findViewById(R.id.lblEntrada);
            lblSaida = (TextView) itemLayoutView.findViewById(R.id.lblSaida);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), DadosHorarioActivity.class);
            i.putExtra("COD_HORARIO", listaHorarios.get(posicao).getCodigo());
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}
