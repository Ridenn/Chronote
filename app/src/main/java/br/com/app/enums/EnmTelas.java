package br.com.app.enums;

/**
 * Created by Wesley on 01/10/2016.
 */
public enum EnmTelas {

    APP_LOGIN(0),
    APP_SOBRE(2),
    APP_SPLASHSCREEN(3),
    APP_CAD_USUARIO(4),
    APP_PESQ_USUARIO(5),
    APP_DADOS_USUARIO(6),
    APP_CAD_HORARIO(7),
    APP_PESQ_HORARIO(8),
    APP_DADOS_HORARIO(9),
    APP_CAD_ANOTACAO(10),
    APP_PESQ_ANOTACAO(11),
    APP_DADOS_ANOTACAO(12);

    public int getEnmTela;
    EnmTelas(int valor) {
        getEnmTela = valor;
    }
}
