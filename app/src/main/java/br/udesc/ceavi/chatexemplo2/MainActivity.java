package br.udesc.ceavi.chatexemplo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoApagar;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoMensagem;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoPintura;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoPontoDestino;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoPontoOrigem;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoUserDisconected;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoUserJoined;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Conexao oCon;

    private FloatingActionButton fBtnChat;
    private FloatingActionButton fBtnBorracha;

    private Canvas canvas;

    private ConstraintLayout constrainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fBtnBorracha = findViewById(R.id.fBtnBorracha);
        fBtnChat     = findViewById(R.id.fBtnChat);

        iniciarConexao();

        constrainLayout = findViewById(R.id.lt);
        constrainLayout.addView(canvas = new Canvas(this));

        canvas.invalidate();
        constrainLayout.invalidate();
        constrainLayout.requestLayout();

        eventosTela();
    }

    private void iniciarConexao() {
        Intent login = getIntent();

        oCon = Conexao.getInstance();
        oCon.setContext(this);
        oCon.setHost(login.getStringExtra("ip"));
        oCon.setPorta(login.getStringExtra("porta"));
        oCon.conectar();

        Socket socket = oCon.getSocket();

        socket.on(Conexao.USER_CONECTED   , new EventoUserJoined(this));//recebe as mensagens
        socket.on(Conexao.USER_DISCONECTED, new EventoUserDisconected(this));//recebe as mensagens
        socket.on(Conexao.O_PONTO         , new EventoPontoOrigem(this));
        socket.on(Conexao.D_PONTO         , new EventoPontoDestino(this));
        socket.on(Conexao.APAGAR          , new EventoApagar(this));

        oCon.conectarUsuario(login.getStringExtra("username"));
    }

    private void eventosTela() {
        fBtnBorracha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvas.apagar();
                canvas.invalidate();
            }
        });
        fBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oTransicao = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(oTransicao);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                //define o ponto  onde o caminho começa
                canvas.moverPara((int)event.getX(), (int)event.getY());
                canvas.invalidate();

                Log.d("teste","Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                //desenha um caminho a partir do ponto inicial
                canvas.linhaPara((int)event.getX(), (int)event.getY());
                canvas.invalidate();

                Log.d("teste","Action was MOVE");
                Log.d("X","" + event.getX());
                Log.d("Y","" + event.getY());
                Log.d("X con cast","" + (int)event.getX());
                Log.d("Y com cast","" + (int)event.getY());
                return true;
            case (MotionEvent.ACTION_UP) :

                Log.d("teste","Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d("teste","Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d("teste","Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public void pintar() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canvas.invalidate();
                Log.d("wsdsds", "repintar");
            }
        });
    }

    public void atualizaCanvas() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canvas.invalidate();
            }
        });
    }

    public void enviarPontoInicial(final float x, final float y) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.enviarPontoInicioLinha(x, y);
            }
        });
    }

    public void enviarPontoDestino(final float x, final float y) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.enviarPontoDestinoLinha(x, y);
            }
        });
    }
    public void apagarCaminho() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.solicitaApagarCaminhoUsuario();
            }
        });
    }

    public void informativo(final String sUserName, final String sMensagem) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, sUserName + ", " + sMensagem, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oCon.desconectar();
    }
}
