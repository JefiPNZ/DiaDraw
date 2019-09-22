package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import br.udesc.ceavi.chatexemplo2.Conexao;
import br.udesc.ceavi.chatexemplo2.MainActivity;

public class EventoUserDisconected extends Evento {

    private static final String TAG = "EventoUserDisconected";

    public EventoUserDisconected(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        Log.d(TAG, "Usuario desconectado");

        try {

            JSONObject oDados = (JSONObject) args[0];

            String sNome = oDados.getString("username");

            MainActivity m = (MainActivity) oContexto;
            m.informativo(sNome, "deixou-nos");

            Conexao.getInstance().removeUsuario(sNome);
            m.atualizaCanvas();

        } catch (JSONException e) {
            Log.e(TAG, "erro", e);
        }
    }
}
