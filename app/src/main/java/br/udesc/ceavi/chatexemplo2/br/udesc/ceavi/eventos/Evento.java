package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos;

import android.content.Context;

import io.socket.emitter.Emitter;

public abstract class Evento implements Emitter.Listener {

    protected Context oContexto;

    public Evento(Context oContexto) {
        this.oContexto = oContexto;
    }
}
