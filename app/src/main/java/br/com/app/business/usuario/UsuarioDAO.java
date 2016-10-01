package br.com.app.business.usuario;

import java.util.LinkedList;

/**
 * Created by Wesley on 01/10/2016.
 */
public class UsuarioDAO extends Usuario {

    private UsuarioBD con;

    public UsuarioDAO(UsuarioBD con){
        this.con = con;
    }

    public LinkedList<Usuario> carregar(){
        return con.carregar(0, getCodigo());
    }

    public boolean carregarEspecifico(){
        LinkedList<Usuario> lista = con.carregar(1, getCodigo());

        if(lista == null || lista.isEmpty()){
            return false;
        }

        Usuario objUsuario = lista.get(0);
        setNome(objUsuario.getNome());
        setEmpresa(objUsuario.getEmpresa());
        setCargo(objUsuario.getCargo());
        setFoto(objUsuario.getFoto());

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
