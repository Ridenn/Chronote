package br.com.app.business.anotacao;

import java.util.LinkedList;

/**
 * Created by Wesley on 02/10/2016.
 */
public class AnotacaoDAO extends Anotacao {

    private AnotacaoBD con;

    public AnotacaoDAO(AnotacaoBD con){
        this.con = con;
    }

    public LinkedList<Anotacao> carregar(){
       return con.carregar(0, getCodigo(), getUsuario());
    }

    public boolean carregarEspecifico(){
        LinkedList<Anotacao> lista = con.carregar(1, getCodigo(), getUsuario());

        if(lista == null || lista.isEmpty()){
            return false;
        }

        Anotacao objAnotacao = lista.get(0);
        setCodigo(objAnotacao.getCodigo());
        setDescricao(objAnotacao.getDescricao());

        return true;
    }

    public boolean excluir(){
        return con.excluir(this);
    }

    public boolean salvar(){
        return con.salvar(this, 0);
    }

    public boolean editar(){
        return con.salvar(this, 1);
    }
}
