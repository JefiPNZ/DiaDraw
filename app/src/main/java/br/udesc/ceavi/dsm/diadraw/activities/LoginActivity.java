package br.udesc.ceavi.dsm.diadraw.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.udesc.ceavi.diadraw.activities.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNome;
    private EditText etIp;
    private EditText etPorta;
    private Button   btnConectar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etNome  = findViewById(R.id.etNome);
        etIp    = findViewById(R.id.etIp);
        etPorta = findViewById(R.id.etPorta);

        btnConectar = findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);


        etNome.setText(getNomeDevice());
//        addEventos();
//        main.gete
    }

//    private void addEventos() {
//        btnConectar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        Intent oTransicao = new Intent(this, MainActivity.class);

        oTransicao.putExtra("username", etNome.getText().toString());
        oTransicao.putExtra("ip", etIp.getText().toString());
        oTransicao.putExtra("porta", etPorta.getText().toString());

        startActivity(oTransicao);
    }


    public String getNomeDevice() {
        //String sNome = Build.MANUFACTURER;
        String sModel = Build.MODEL;

        if (sModel.startsWith(sModel)) {
            capitalize(sModel);
        }
        return capitalize(sModel);
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
}




