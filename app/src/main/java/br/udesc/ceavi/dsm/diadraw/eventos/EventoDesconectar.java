package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

public class EventoDesconectar extends Evento {

    private static final String TAG = "EventoDesconectar";

    public EventoDesconectar(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        Log.d(TAG, "desconectado");
    }

}
