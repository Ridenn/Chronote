package br.com.app.activity.sobre;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.app.activity.R;

import android.widget.TextView;

/**
 * Created by Wesley on 01/10/2016.
 */
public class AppSobreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_sobre);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        carregar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){
        TextView lblVersao = (TextView) findViewById(R.id.lblVersao);

        try {
            lblVersao.setText("Versão " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
