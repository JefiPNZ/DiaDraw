package br.udesc.ceavi.chatexemplo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
}
