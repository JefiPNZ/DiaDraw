package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

public class EventoConexaoErro extends Evento {

    private static final String TAG = "EventoConexaoErro";

    public EventoConexaoErro(Context oContexto) {

        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        Log.d(TAG, "erro na conexão");
    }
}
