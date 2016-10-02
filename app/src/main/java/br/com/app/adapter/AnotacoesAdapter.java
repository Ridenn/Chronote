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
import br.com.app.activity.anotacao.DadosAnotacaoActivity;
import br.com.app.business.anotacao.Anotacao;

/**
 * Created by Wesley on 02/10/2016.
 */
public class AnotacoesAdapter extends RecyclerView.Adapter<AnotacoesAdapter.ViewHolder> {

    private Context context;
    private static List<Anotacao> listaAnotacoes;

    public AnotacoesAdapter(Context context, List<Anotacao> listaAnotacoes) {
        this.context = context;
        this.listaAnotacoes = listaAnotacoes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anotacoes, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Anotacao objAnotacao = listaAnotacoes.get(position);

        String descricao = objAnotacao.getDescricao();
        if(descricao.length() > 40){
            descricao = descricao.substring(0, 40) + "...";
        }
        viewHolder.lblCapaAnotacao.setText(descricao);

        viewHolder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return listaAnotacoes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int posicao;

        public TextView lblCapaAnotacao;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            lblCapaAnotacao = (TextView) itemLayoutView.findViewById(R.id.lblCapaAnotacao);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), DadosAnotacaoActivity.class);
            i.putExtra("COD_ANOTACAO", listaAnotacoes.get(posicao).getCodigo());
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}
