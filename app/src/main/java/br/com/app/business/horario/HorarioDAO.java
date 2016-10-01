package br.com.app.business.horario;

import java.util.LinkedList;

/**
 * Created by Wesley on 01/10/2016.
 */
public class HorarioDAO extends Horario{

    private HorarioBD con;

    public HorarioDAO(HorarioBD con){
        this.con = con;
    }

    public LinkedList<Horario> carregar(){
        return con.carregar(0, getCodigo(), getUsuario());
    }

    public boolean carregarEspecifico(){
        LinkedList<Horario> lista = con.carregar(1, getCodigo(), getUsuario());

        if(lista == null || lista.isEmpty()){
            return false;
        }

        Horario objHorario = lista.get(0);
        setData(objHorario.getData());
        setEntrada(objHorario.getEntrada());
        setSaida(objHorario.getSaida());

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
