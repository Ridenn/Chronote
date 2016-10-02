package br.com.app.business.anotacao;

/**
 * Created by Wesley on 02/10/2016.
 */
public class Anotacao {

    private int codigo;
    private int usuario;
    private String descricao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
