package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import br.udesc.ceavi.chatexemplo2.Conexao;
import br.udesc.ceavi.chatexemplo2.MainActivity;

public class EventoPontoDestino extends Evento {

    private static final String TAG = "PontoDestino";

    public EventoPontoDestino(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];

            String      sNome = oDados.getString("username");
            JSONObject oPonto = oDados.getJSONObject("ponto");

            Conexao.getInstance().atualizaPontoDestinoUsuario(sNome, oPonto.getInt("x"), oPonto.getInt("y"));

            MainActivity m = (MainActivity) oContexto;
            m.pintar();

        } catch (Exception ex) {
            Log.e(TAG, "erro", ex);
        }
    }
}
