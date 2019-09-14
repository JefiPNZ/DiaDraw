package br.udesc.ceavi.chatexemplo2;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.Evento;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoConectar;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoConexaoErro;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoDesconectar;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoMensagem;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoPintura;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoUserDisconected;
import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos.EventoUserJoined;
import io.socket.client.IO;
import io.socket.client.Socket;

public class Conexao {

    private static final String TAG = "Conexao";

    private static Conexao oInstance;

    private final String URL_PADRAO       = "http://localhost:3333";
    private final String MENSAGEM         = "sMSG";
    private final String ADD_USER         = "addUser";
    private final String USER_DISCONECTED = "uDesconectado";
    private final String USER_CONECTED    = "uConectado";
    private final String PONTOS           = "pontos";

    private Socket  oSocket;
    private Context oContext;
    private String  sHost;
    private String  sPorta;

    private EventoUserDisconected oUserDisconected;
    private EventoConectar        oConectar;
    private EventoDesconectar     oDesconectar;
    private EventoConexaoErro     oConexaoErro;
    private EventoMensagem        oMensagem;
    private EventoUserJoined      oUserJoined;
    private EventoPintura         oPintura;

    private Conexao() {}

    public static Conexao getInstance() {
        if(oInstance == null) {
            oInstance = new Conexao();
        }
        return oInstance;
    }

    public void setContext(Context oCon) {
        this.oContext = oCon;
    }

    private void defineEventos() {
        oUserDisconected = new EventoUserDisconected(oContext);
        oConectar        = new EventoConectar(oContext);
        oDesconectar     = new EventoDesconectar(oContext);
        oConexaoErro     = new EventoConexaoErro(oContext);
        oMensagem        = new EventoMensagem(oContext);
        oUserJoined      = new EventoUserJoined(oContext);
        oPintura         = new EventoPintura(oContext);
    }

    public void setHost(String sHost) {
        this.sHost = sHost;
    }

    public void setPorta(String sPorta) {
        this.sPorta = sPorta;
    }

    public void conectar() {
        try {

            defineEventos();

            oSocket = IO.socket("http://" + this.sHost + ":" + this.sPorta);
//            oSocket = IO.socket(URL_PADRAO);

            oSocket.on(Socket.EVENT_CONNECT        , oConectar);
            oSocket.on(Socket.EVENT_DISCONNECT     , oDesconectar);
            oSocket.on(Socket.EVENT_CONNECT_ERROR  , oConexaoErro);
            oSocket.on(Socket.EVENT_CONNECT_TIMEOUT, oConexaoErro);

            oSocket.on(MENSAGEM        , oMensagem);//recebe as mensagens
            oSocket.on(USER_CONECTED   , oUserJoined);//recebe as mensagens
            oSocket.on(USER_DISCONECTED, oUserDisconected);//recebe as mensagens
            oSocket.on(PONTOS          , oPintura);

            oSocket.connect();

        } catch (URISyntaxException e) {
            Log.e(TAG, "erro na classe Conexao", e);
        }
    }

    public void desconectar() {
        oSocket.disconnect();
        oSocket.off(MENSAGEM, oMensagem);
    }

    public void enviarMensagem(String sMen) {
        oSocket.emit(MENSAGEM, sMen);
    }

    public void enviarPontos(List<Point> pontos) {

        JSONObject[] objetos = new JSONObject[pontos.size()];

        try {
            int cont = 0;
            for(Point p : pontos) {
                JSONObject obj = new JSONObject();

                obj.put("x", p.x);
                obj.put("y", p.y);

                objetos[cont] = obj;
                cont++;
            }
        } catch (JSONException e) {
            Log.e(TAG, "erro", e);
        }

        oSocket.emit(PONTOS, objetos);
    }

    public void conectarUsuario(String userName) {
        oSocket.emit(ADD_USER, userName);
    }
}
