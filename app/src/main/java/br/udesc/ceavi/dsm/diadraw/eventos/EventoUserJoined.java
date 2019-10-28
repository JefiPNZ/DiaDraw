package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import br.udesc.ceavi.dsm.diadraw.Conexao;
import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;
import br.udesc.ceavi.dsm.diadraw.model.ModelUsuario;

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
            int    cor = oDados.getInt("color");

            incluirUsuario(sNome, cor);

            MainActivity m = (MainActivity) oContexto;
            m.informativo(sNome, "juntou-se a nós");

        } catch (JSONException e) {
            Log.e(TAG, "erro", e);
        }
    }

    private void incluirUsuario(String nome, int cor) {
        Conexao oCon = Conexao.getInstance();
        ModelUsuario usuario = new ModelUsuario(oContexto);
        usuario.setNome(nome);
        usuario.setCor(cor);

        oCon.adicionaUsuario(usuario);
    }
}
