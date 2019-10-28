package br.udesc.ceavi.dsm.diadraw.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.udesc.ceavi.diadraw.activities.R;
import br.udesc.ceavi.dsm.diadraw.EnumTipoPintura;
import br.udesc.ceavi.dsm.diadraw.ViewCanvas;
import br.udesc.ceavi.dsm.diadraw.Conexao;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoApagar;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoPontoDestino;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoPontoMovimento;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoPontoOrigem;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoTipoPintura;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoUserDisconected;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoUserJoined;
import br.udesc.ceavi.dsm.diadraw.model.ModelUsuario;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {

    private Conexao oCon;

    private FloatingActionButton fBtnChat;
    private FloatingActionButton fBtnBorracha;

    private ViewCanvas viewCanvas;

    private ConstraintLayout constrainLayout;
    private boolean bApagar;

    private ModelUsuario oUsuarioConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fBtnBorracha = findViewById(R.id.fBtnBorracha);
        fBtnChat     = findViewById(R.id.fBtnChat);

        iniciarConexao();

        constrainLayout = findViewById(R.id.lt);
        constrainLayout.addView(viewCanvas = new ViewCanvas(this, oUsuarioConectado));
//        constrainLayout.setBackgroundColor(Color.RED);


        viewCanvas.invalidate();
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
        socket.on(Conexao.M_PONTO         , new EventoPontoMovimento(this));
        socket.on(Conexao.APAGAR          , new EventoApagar(this));
        socket.on(Conexao.MUDANCA_PINTURA , new EventoTipoPintura(this));

        oUsuarioConectado = new ModelUsuario(this);
        oUsuarioConectado.setNome(login.getStringExtra("username"));
        oUsuarioConectado.setCor(ModelUsuario.getCorAleatoria());

        oCon.conectarUsuario(login.getStringExtra("username"), ModelUsuario.getCorAleatoria());
    }

    private void eventosTela() {
        fBtnBorracha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewCanvas.apaga) {
                    viewCanvas.apaga = false;
                } else {
                    viewCanvas.apaga = true;
                }
                viewCanvas.apagar();
//                canvas.invalidate();
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//
//        int action = event.getAction();
//
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                //define o ponto  onde o caminho começa
//                viewCanvas.moverPara((int)event.getX(), (int)event.getY());
//                viewCanvas.invalidate();
//
//                Log.d("teste","Action was DOWN");
//                return true;
//            case (MotionEvent.ACTION_MOVE) :
//                //desenha um caminho a partir do ponto inicial
//                viewCanvas.linhaPara((int)event.getX(), (int)event.getY());
//                viewCanvas.invalidate();
//
//                Log.d("teste","Action was MOVE");
//                Log.d("X","" + event.getX());
//                Log.d("Y","" + event.getY());
//                Log.d("X con cast","" + (int)event.getX());
//                Log.d("Y com cast","" + (int)event.getY());
//                return true;
//            case (MotionEvent.ACTION_UP) :
//
//                Log.d("teste","Action was UP");
//                return true;
//            case (MotionEvent.ACTION_CANCEL) :
//                Log.d("teste","Action was CANCEL");
//                return true;
//            case (MotionEvent.ACTION_OUTSIDE) :
//                Log.d("teste","Movement occurred outside bounds " +
//                        "of current screen element");
//                return true;
//            default :
//                return super.onTouchEvent(event);
//        }
//    }

    public void pintar() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewCanvas.invalidate();
                Log.d("wsdsds", "repintar");
            }
        });
    }

    public void atualizaCanvas() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewCanvas.invalidate();
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

    public void enviarPontoDestino() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.enviarPontoDestinoLinha();
            }
        });
    }

    public void enviarPontoMovimento(final float x, final float y) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.enviarPontoMovimento(x, y);
            }
        });
    }

    public void solicitaMudancaPintura(final EnumTipoPintura tipo) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.solicitaMudancaPintura(tipo);
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
