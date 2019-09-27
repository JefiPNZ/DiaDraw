package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

public class EventoConectar extends Evento {

    private static final String TAG = "EventoConectado";

    public EventoConectar(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
       Log.d(TAG, "conexão feita com sucesso");
    }
}
