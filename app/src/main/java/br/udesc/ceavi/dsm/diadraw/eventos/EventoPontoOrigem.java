package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import br.udesc.ceavi.dsm.diadraw.Conexao;
import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;

public class EventoPontoOrigem extends Evento {

    private static final String TAG = "PontoOrigem";

    public EventoPontoOrigem(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];

            String      sNome = oDados.getString("username");
            JSONObject oPonto = oDados.getJSONObject("ponto");

            Conexao.getInstance().atualizaPontoInicialUsuario(sNome, oPonto.getInt("x"), oPonto.getInt("y"));

            MainActivity m = (MainActivity) oContexto;
            m.pintar();

        } catch (Exception ex) {
            Log.e(TAG, "erro", ex);
        }
    }
}
