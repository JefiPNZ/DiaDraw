package br.udesc.ceavi.chatexemplo2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoMensagem;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.model.ModelAdapterMensagem;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.model.ModelMensagem;
import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {

    private ModelAdapterMensagem oAdapterMensagem;
    private ListView ltView;

    private EditText input;
    private Button   btnEnviar;

    private Conexao oCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);


        oAdapterMensagem = new ModelAdapterMensagem(this);

        ltView = findViewById(R.id.ltView);

        input       = findViewById(R.id.input);
        btnEnviar   = findViewById(R.id.btnEnviar);

        oCon = Conexao.getInstance();

        ltView.setAdapter(oAdapterMensagem);
        Socket socket = oCon.getSocket();
        socket.on(Conexao.MENSAGEM, new EventoMensagem(this));

        eventosTela();
    }

    private void eventosTela() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar();
            }
        });
    }


    private void enviar() {
        String sMsg = input.getText().toString();
        if(TextUtils.isEmpty(sMsg)) {
            return;
        }

        anexarMensagem("this", sMsg);
        input.setText("");
        oCon.enviarMensagem(sMsg);
    }

    public void anexarMensagem(final String sNome, final String sMsg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ModelMensagem m = new ModelMensagem(sNome, sMsg);
                oAdapterMensagem.addMensagem(m);
                oAdapterMensagem.notifyDataSetChanged();
                ltView.setSelection(ltView.getCount() - 1);
            }
        });
    }
}
