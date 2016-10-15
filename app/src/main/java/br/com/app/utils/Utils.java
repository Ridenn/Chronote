package br.com.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.app.activity.R;
import br.com.app.activity.anotacao.AnotacoesActivity;
import br.com.app.activity.anotacao.CadastrarAnotacaoActivity;
import br.com.app.activity.anotacao.DadosAnotacaoActivity;
import br.com.app.activity.horario.CadastrarHorarioActivity;
import br.com.app.activity.horario.DadosHorarioActivity;
import br.com.app.activity.horario.HorariosActivity;
import br.com.app.activity.splashscreen.AppSplashScreenActivity;
import br.com.app.activity.sobre.AppSobreActivity;
import br.com.app.activity.usuario.CadastrarUsuarioActivity;
import br.com.app.activity.usuario.DadosUsuarioActivity;
import br.com.app.activity.usuario.UsuarioActivity;
import br.com.app.enums.EnmTelas;

/**
 * Created by Wesley on 10/09/2016.
 */
public class Utils {

    public static void chamarActivity(Activity activity, EnmTelas enmActivity) {
        chamarActivity(activity, enmActivity, "", false);
    }

    public static void chamarActivity(Activity activity, EnmTelas enmActivity, String extras, boolean valExtras) {
        Intent i = new Intent();
        Class classe = null;

        try {
            switch (enmActivity) {
                case APP_SOBRE:
                    classe = AppSobreActivity.class;
                    break;
                case APP_SPLASHSCREEN:
                    classe = AppSplashScreenActivity.class;
                    break;
                case APP_CAD_USUARIO:
                    classe = CadastrarUsuarioActivity.class;
                    break;
                case APP_DADOS_USUARIO:
                    classe = DadosUsuarioActivity.class;
                    break;
                case APP_PESQ_USUARIO:
                    classe = UsuarioActivity.class;
                    break;
                case APP_CAD_HORARIO:
                    classe = CadastrarHorarioActivity.class;
                    break;
                case APP_DADOS_HORARIO:
                    classe = DadosHorarioActivity.class;
                    break;
                case APP_PESQ_HORARIO:
                    classe = HorariosActivity.class;
                    break;
                case APP_CAD_ANOTACAO:
                    classe = CadastrarAnotacaoActivity.class;
                    break;
                case APP_DADOS_ANOTACAO:
                    classe = DadosAnotacaoActivity.class;
                    break;
                case APP_PESQ_ANOTACAO:
                    classe = AnotacoesActivity.class;
                    break;
            }

            i.setClass(activity, classe);

            if (!extras.equals("")) {
                i.putExtra(extras, valExtras);
            }

            activity.startActivity(i);


//                activity.finish();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean soNumeros(String texto){

        if(texto == null || texto.isEmpty()) {
            return false;
        }

        for (char letra : texto.toCharArray()) {
            if (letra < '0' || letra > '9') {
                return false;
            }
        }

        return true;
    }

    public static boolean soTexto(String texto){
        for(char letra : texto.toCharArray()){
            if(Character.isDigit(letra)){
                return false;
            }
        }

        return true;
    }

    public static boolean temCaractereEspecial(String texto) {
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(texto);
        return m.find();
    }

    public static String removerAcento(String texto){
        String nfdNormalizedString = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String preencher(int campo, String preenchimento, int tamanho){
        String aux = "";
        int qtd = tamanho - String.valueOf(campo).length();
        while(aux.length() < qtd){
            aux += preenchimento;
        }
        return aux + campo;
    }

    public static void compartilharAnotacao(View view, String txtTitulo){

        Intent iCompartilhar = new Intent(Intent.ACTION_SEND);
        iCompartilhar.setType("text/plain");
        iCompartilhar.putExtra(Intent.EXTRA_TEXT, ("Ei! Olha a anotação que eu fiz no Chronote:\n_____________________\n"
                + "*" + txtTitulo + "*" + "\n" + ((EditText) view).getText().toString() + "\n" +
                "_____________________"));
        view.getContext().startActivity(Intent.createChooser(iCompartilhar, ((EditText) view).getText().toString()));
    }
}