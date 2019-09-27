package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import br.udesc.ceavi.dsm.diadraw.activities.ChatActivity;

public class EventoMensagem extends Evento {

    private static final String TAG = "EventoMensagem";

    public EventoMensagem(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];

            String sNome = oDados.getString("username");
            String sMsg  = oDados.getString("msg");

            ChatActivity m = (ChatActivity) oContexto;

            m.anexarMensagem(sNome, sMsg);

        } catch (JSONException e) {
            Log.e(TAG,"evento mensagem erro", e);
        }
    }
}
