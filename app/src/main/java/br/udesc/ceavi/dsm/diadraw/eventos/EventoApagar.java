package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import br.udesc.ceavi.dsm.diadraw.Conexao;
import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;

public class EventoApagar extends Evento {

    private static final String TAG = "EventoApagar";

    public EventoApagar(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];

            String      sNome = oDados.getString("username");

            Conexao.getInstance().apagarCaminhoUsuario(sNome);

            MainActivity m = (MainActivity)oContexto;
            m.atualizaCanvas();

        } catch (Exception ex) {
            Log.e(TAG, "erro", ex);
        }
    }
}
