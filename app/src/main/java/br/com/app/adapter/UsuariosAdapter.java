package br.com.app.adapter;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.app.activity.R;
import br.com.app.activity.anotacao.AnotacoesActivity;
import br.com.app.activity.horario.DadosHorarioActivity;
import br.com.app.activity.horario.HorariosActivity;
import br.com.app.activity.usuario.DadosUsuarioActivity;
import br.com.app.business.anotacao.Anotacao;
import br.com.app.business.horario.Horario;
import br.com.app.business.usuario.Usuario;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Lucas on 10/09/2016.
 */
public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    private Context context;
    private static List<Usuario> listaUsuarios;

    public UsuariosAdapter(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_usuarios, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Usuario objUsuario = listaUsuarios.get(position);

        viewHolder.lblUsuario.setText(objUsuario.getNome());
        if(objUsuario.getFoto() == null){
            viewHolder.imgUsuario.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        } else {
            viewHolder.imgUsuario.setImageBitmap(Bitmap.createScaledBitmap(objUsuario.getFoto(), 100, 100, false));
        }
        viewHolder.lblHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Horario.codUsuario = objUsuario.getCodigo();
                Intent i = new Intent(context, HorariosActivity.class);
                context.startActivity(i);;
            }
        });
        viewHolder.lblAnotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Anotacao.codUsuario = objUsuario.getCodigo();
                Intent i = new Intent(context, AnotacoesActivity.class);
                context.startActivity(i);;
            }
        });
        viewHolder.setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int posicao;

        public TextView lblUsuario;
        public ImageView imgUsuario;
        public TextView lblHorario;
        public TextView lblAnotacao;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            lblUsuario = (TextView) itemLayoutView.findViewById(R.id.lblUsuario);
            imgUsuario = (ImageView) itemLayoutView.findViewById(R.id.imgUsuario);
            lblHorario = (TextView) itemLayoutView.findViewById(R.id.lblHorario);
            lblAnotacao = (TextView) itemLayoutView.findViewById(R.id.lblAnotacoes);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), DadosUsuarioActivity.class);
            i.putExtra("COD_USUARIO", listaUsuarios.get(posicao).getCodigo());
            view.getContext().startActivity(i);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }
    }
}
