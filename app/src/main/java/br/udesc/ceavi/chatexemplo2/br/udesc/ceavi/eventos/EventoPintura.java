package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.eventos;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.udesc.ceavi.chatexemplo2.MainActivity;

public class EventoPintura extends Evento {

    private static final String TAG = "Pintura";

    public EventoPintura(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];

            JSONArray ponto = oDados.getJSONArray("pontos");


            List<Point> p = new ArrayList<>();

//            p.add(new Point(ponto.getInt("x"), ponto.getInt("y")));

            for(int i = 0; i < ponto.length(); i++) {
                p.add(new Point(ponto.getJSONObject(i).getInt("x"), ponto.getJSONObject(i).getInt("y")));
            }

            MainActivity m = (MainActivity) oContexto;
            m.pintar(p);
        } catch (JSONException e) {
            Log.e(TAG, "erro", e);
        }
    }
}
