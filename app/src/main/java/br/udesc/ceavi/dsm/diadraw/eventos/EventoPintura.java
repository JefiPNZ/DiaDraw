package br.udesc.ceavi.dsm.diadraw.eventos;

import android.content.Context;
import android.graphics.Path;
import android.util.Log;

import org.json.JSONObject;

import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;

@Deprecated
public class EventoPintura extends Evento {

    private static final String TAG = "Pintura";

    public EventoPintura(Context oContexto) {
        super(oContexto);
    }

    @Override
    public void call(Object... args) {
        try {
            JSONObject oDados = (JSONObject) args[0];


            String sNome       = oDados.getString("username");
            JSONObject oObjeto = oDados.getJSONObject("caminho");

            //JSONArray ponto = oDados.getJSONArray("pontos");
            Object caminho = oObjeto.get("caminho");
            Path pasasa = (Path) caminho;
//            caminho.getJSONObject("android.graphics.Path@99a0933");
//            pasa.set( );

//            List<Point> p = new ArrayList<>();
//
//
//            for(int i = 0; i < ponto.length(); i++) {
//                p.add(new Point(ponto.getJSONObject(i).getInt("x"), ponto.getJSONObject(i).getInt("y")));
//            }
//
//            Conexao.getInstance().atualizaCaminhoUsuario(sNome, caminho);

            MainActivity m = (MainActivity) oContexto;
            m.pintar();
        } catch (Exception e) {
            Log.e(TAG, "erro", e);
        }
    }
}
