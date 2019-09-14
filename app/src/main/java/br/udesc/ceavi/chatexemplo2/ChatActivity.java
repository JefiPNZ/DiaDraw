package br.udesc.ceavi.chatexemplo2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private TextView tvAreaTexto;
    private EditText input;
    private Button   btnEnviar;

    private Conexao oCon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        tvAreaTexto = findViewById(R.id.tvAreaTexto);
        input       = findViewById(R.id.input);
        btnEnviar   = findViewById(R.id.btnConectar);

        oCon = Conexao.getInstance();

        eventosTela();
    }


    private void eventosTela() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar();
            }
        });

//        btnLogar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent oTransicao = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(oTransicao);
//            }
//        });
    }


    private void enviar() {
        String sMsg = input.getText().toString();
        if(TextUtils.isEmpty(sMsg)) {
            return;
        }

        input.setText("");
        oCon.enviarMensagem(sMsg);
    }

    public void anexarMensagem(final String sNome, final String sMsg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvAreaTexto.append("\n" + sNome + ": " + sMsg + "\n");
            }
        });
    }
}
