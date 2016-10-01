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
import br.com.app.activity.usuario.DadosUsuarioActivity;
import br.com.app.business.usuario.Usuario;

/**
 * Created by Wesley on 10/09/2016.
 */
public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    private Context context;
    private static List<Usuario> listaUsuarios;

    public UsuariosAdapter(Context context, List<Usuario> listaDiscussoes) {
        this.context = context;
        this.listaUsuarios = listaDiscussoes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_usuarios, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Usuario objAcesso = listaUsuarios.get(position);

        viewHolder.lblUsuario.setText(objAcesso.getNome());
        if(objAcesso.getFoto() == null){
            viewHolder.imgUsuario.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        } else {
            viewHolder.imgUsuario.setImageBitmap(Bitmap.createScaledBitmap(objAcesso.getFoto(), 100, 100, false));
        }
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

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);

            lblUsuario = (TextView) itemLayoutView.findViewById(R.id.lblUsuario);
            imgUsuario = (ImageView) itemLayoutView.findViewById(R.id.imgUsuario);
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
