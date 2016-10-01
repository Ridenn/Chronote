package br.com.app.business.horario;

import java.util.Date;

/**
 * Created by Wesley on 01/10/2016.
 */
public class Horario {

    private int codigo;
    private int usuario;
    private Date data;
    private Date entrada;
    private Date saida;

    public static int codUsuario = 0;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getUsuario() {
        usuario = codUsuario;
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public Date getSaida() {
        return saida;
    }

    public void setSaida(Date saida) {
        this.saida = saida;
    }
}
