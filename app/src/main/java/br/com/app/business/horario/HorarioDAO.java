package br.com.app.business.horario;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import br.com.app.utils.FuncoesData;

/**
 * Created by Wesley on 01/10/2016.
 */
public class HorarioDAO extends Horario{

    private HorarioBD con;

    public HorarioDAO(HorarioBD con){
        this.con = con;
    }

    public LinkedList<Horario> carregar(){

        LinkedList<Horario> lista = con.carregar(0, getCodigo(), getUsuario());

        if(lista == null || lista.isEmpty()){
            return null;
        }

        Collections.sort(lista, new Comparator<Horario>() {
            public int compare(Horario h1, Horario h2) {
                if (h1.getData() == null || h2.getData() == null) {
                    return 0;
                }
                return FuncoesData.comparaDatas(h1.getData(), h2.getData()) == 1 ? -1 : 1;
            }
        });

        return lista;
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
