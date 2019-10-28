package br.udesc.ceavi.dsm.diadraw;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

import br.udesc.ceavi.dsm.diadraw.eventos.EventoConectar;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoConexaoErro;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoDesconectar;
import br.udesc.ceavi.dsm.diadraw.eventos.EventoMensagem;
import br.udesc.ceavi.dsm.diadraw.model.ModelUsuario;
import io.socket.client.IO;
import io.socket.client.Socket;

public class Conexao {

    private static final String TAG = "Conexao";

    private static Conexao oInstance;

    private final String URL_PADRAO       = "http://localhost:3333";
    public static final String MENSAGEM         = "sMSG";
    public static final String ADD_USER         = "addUser";
    public static final String USER_DISCONECTED = "uDesconectado";
    public static final String USER_CONECTED    = "uConectado";
    public static final String O_PONTO          = "ponto_origem";
    public static final String D_PONTO          = "ponto_destino";
    public static final String M_PONTO          = "ponto_movimento";
    public static final String MUDANCA_PINTURA  = "mudanca_pintura";
    @Deprecated
    public static final String APAGAR           = "apagar";

    private HashMap<String, ModelUsuario> usuarios;

    private Socket  oSocket;
    private Context oContext;
    private String  sHost;
    private String  sPorta;

    private EventoConectar oConectar;
    private EventoDesconectar oDesconectar;
    private EventoConexaoErro oConexaoErro;
    private EventoMensagem oMensagem;

    private Conexao() {
        usuarios = new HashMap<>();
    }

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
        oConectar        = new EventoConectar(oContext);
        oDesconectar     = new EventoDesconectar(oContext);
        oConexaoErro     = new EventoConexaoErro(oContext);
        oMensagem        = new EventoMensagem(oContext);
    }

    private JSONObject getJsonObject(String[] nameParams, Object[] valueParams) {
        JSONObject obj = new JSONObject();
        try {
            for (int i = 0; i < valueParams.length; i++ ) {
                obj.put(nameParams[i], valueParams[i]);
            }
        } catch (Exception ex) {
            Log.e(TAG, "erro na atribuição de valor ao objeto json", ex);
        }
        return obj;
    }

    private ModelUsuario getUsuario(String nome) {
        ModelUsuario usuario = null;
        if(!usuarios.containsKey(nome) && !nome.equals("")) {
            usuario = new ModelUsuario(oContext);
            usuario.setNome(nome);
            adicionaUsuario(usuario);
        } else {
            usuario = usuarios.get(nome);
        }
        return usuario;
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

            oSocket.on(Socket.EVENT_CONNECT        , oConectar);
            oSocket.on(Socket.EVENT_DISCONNECT     , oDesconectar);
            oSocket.on(Socket.EVENT_CONNECT_ERROR  , oConexaoErro);
            oSocket.on(Socket.EVENT_CONNECT_TIMEOUT, oConexaoErro);

            oSocket.connect();

        } catch (URISyntaxException e) {
            Log.e(TAG, "erro na classe Conexao", e);
        }
    }

    public void removeUsuario(String nome) {
        this.usuarios.remove(nome);
    }

    public void adicionaUsuario(ModelUsuario usuario) {
        if(usuario.getNome().equals("")) {
            return;
        }
        usuarios.put(usuario.getNome(), usuario);
    }

    public void atualizaPontoInicialUsuario(String nome, float x, float y) {
        ModelUsuario usuario = getUsuario(nome);
        usuario.setPontoInicial(x, y);
    }

    public void atualizaPontoDestinoUsuario(String nome) {
        ModelUsuario usuario = getUsuario(nome);
        usuario.setPontoLinhaDestino();
    }

    public void atualizaPontoMovimentoUsuario(String nome, float x, float y) {
        ModelUsuario usuario = getUsuario(nome);
        usuario.setPontoMover(x, y);
    }

    public void atualizaTipoPintura(String nome, EnumTipoPintura tipo) {
        ModelUsuario usuario = getUsuario(nome);
        usuario.setTipoPintura(tipo);
    }

    public boolean possuiUsuario(String nome) {
        return usuarios.containsKey(nome);
    }

    public HashMap getUsuarios() {
        return usuarios;
    }

    public Socket getSocket() {
        return oSocket;
    }

    public void desconectar() {
        oSocket.disconnect();
        oSocket.off(MENSAGEM, oMensagem);
    }



    public void apagarCaminhoUsuario(String nome) {
        ModelUsuario usuario = getUsuario(nome);
        usuario.setTipoPintura(EnumTipoPintura.APAGAR);
    }

    //Emissao de Eventos

    public void conectarUsuario(String userName, int cor) {
        oSocket.emit(ADD_USER, userName, cor);
    }

    public void enviarMensagem(String sMen) {
        oSocket.emit(MENSAGEM, sMen);
    }

    /**
     * Os pontos onde a linha sera iniciada
     *
     * @param x
     * @param y
     */
    public void enviarPontoInicioLinha(float x, float y) {
        oSocket.emit(O_PONTO, getJsonObject(new String[]{"x", "y"}, new Object[]{x, y}));
    }

    public void enviarPontoMovimento(float x, float y) {
        oSocket.emit(M_PONTO, getJsonObject(new String[]{"x", "y"}, new Object[]{x, y}));
    }

    /**
     * Ponto final da linha
     */
    public void enviarPontoDestinoLinha() {
        oSocket.emit(D_PONTO);
    }

    public void solicitaApagarCaminhoUsuario() {
        oSocket.emit(APAGAR);
    }

    public void solicitaMudancaPintura(EnumTipoPintura tipo) {
        oSocket.emit(MUDANCA_PINTURA, getJsonObject(new String[]{"tipo"}, new Object[]{tipo}));
    }

}
