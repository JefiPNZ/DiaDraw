package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.udesc.ceavi.chatexemplo2.MainActivity;

public class EventoUserJoined extends Evento {

    private static final String TAG = "EventoUserJoined";

    public EventoUserJoined(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        Log.d(TAG, "novo usuario adicionado");

        try {

            JSONObject oDados = (JSONObject) args[0];

            String sNome = oDados.getString("username");

            Toast.makeText(oContexto, sNome + " juntou-se a nós", Toast.LENGTH_LONG);
//            MainActivity m = (MainActivity) oContexto;
//
//            m.informativo(sNome, "juntou-se a nós");

        } catch (JSONException e) {
            Log.e(TAG, "erro", e);
        }
    }
}
