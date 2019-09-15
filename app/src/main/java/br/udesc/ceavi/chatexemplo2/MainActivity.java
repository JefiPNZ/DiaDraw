package br.udesc.ceavi.chatexemplo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Conexao oCon;

    private TextView tvStatus;
    private TextView tvChatInput;
    private Button   btnEnviar;
    private Button   btnLogar;

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
//        tvStatus    = findViewById(R.id.tvStatus);
//        tvChatInput = findViewById(R.id.tvChatInput);
//        btnEnviar   = findViewById(R.id.btnEnviar);
//        btnLogar    = findViewById(R.id.btnLogar);

        iniciarConexao();

        constrainLayout = findViewById(R.id.lt);
        constrainLayout.addView(canvas = new Canvas(this));

//
//        List<Point> pontos = new ArrayList<>();
//
//        pontos.add(new Point(50, 100));
//        pontos.add(new Point(300, 200));
//        pontos.add(new Point(250, 150));
//
//        pintar(pontos);

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

            }
        });
//        btnEnviar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                enviar();
//            }
//        });

//        btnLogar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent oTransicao = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(oTransicao);
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d("teste","Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
//                event.get
//                canvas.setX(event.getX());
//                canvas.setY(event.getY());
                canvas.setPonto((int)event.getX(), (int)event.getY());
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

    private void enviar() {
        String sMsg = tvChatInput.getText().toString();
        if(TextUtils.isEmpty(sMsg)) {
            return;
        }

        tvChatInput.setText("");
        oCon.enviarMensagem(sMsg);
    }

    public void anexarMensagem(final String sNome, final String sMsg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStatus.append("\n" + sNome + ": " + sMsg + "\n");
            }
        });
    }

    public void pintar(final List<Point> pontos) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                canvas.setPontos(pontos);
                canvas.invalidate();
                Log.d("wsdsds", "repintar");
            }
        });
    }

    public void enviarPontos(final List<Point> pontos) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oCon.enviarPontos(pontos);
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
