package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import br.udesc.ceavi.dsm.diadraw.Conexao;
import br.udesc.ceavi.dsm.diadraw.EnumTipoPintura;
import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;

public class EventoTipoPintura extends Evento {

    private static final String TAG = "EventoTipoPintura";

    public EventoTipoPintura(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];

            String      sNome = oDados.getString("username");
            JSONObject  oTipo = oDados.getJSONObject("tipo");

            EnumTipoPintura eTipo = null;

            switch (oTipo.getString("tipo")) {
                case "APAGAR":
                    eTipo = EnumTipoPintura.APAGAR;
                    break;
                case "NORMAL":
                    eTipo = EnumTipoPintura.NORMAL;
                    break;
            }
//            EnumTipoPintura tipo = oTipo.getString("tipo");
//
            Conexao.getInstance().atualizaTipoPintura(sNome, eTipo);
//
//            MainActivity m = (MainActivity) oContexto;
//            m.pintar();

        } catch (Exception ex) {
            Log.e(TAG, "erro", ex);
        }
    }
}
