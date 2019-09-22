package br.udesc.ceavi.chatexemplo2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.model.ModelUsuario;

public class Canvas extends View {

    private Paint pintura;
    private Context con;
    private Conexao conex;

    private Path caminho;

    public Canvas(Context context) {
        super(context);

        con     = context;
        conex   = Conexao.getInstance();
        pintura = new Paint();

//        pintura.setAntiAlias(true);
        pintura.setColor(Color.GREEN);
        pintura.setStyle(Paint.Style.STROKE);
        pintura.setStrokeWidth(5f);
//        pintura.setStrokeJoin(Paint.Join.ROUND);


        caminho = new Path();
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(caminho, pintura);

        if(conex.getUsuarios().size() > 0) {
            Map<String, ModelUsuario> usuarios = conex.getUsuarios();
            for(Map.Entry<String, ModelUsuario> par : usuarios.entrySet()) {
                Path uCaminho = par.getValue().getCaminho();
                if(uCaminho == null) {
                    continue;
                }
                canvas.drawPath(uCaminho, pintura);
            }
        }
    }


    public static float pxFromDp(final Context context, final float dp) {
//        Log.d("ddsdsdsdsds", "densidade" + context.getResources().getDisplayMetrics().density);
//        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public void linhaPara(int x, int y) {
        caminho.lineTo(x, y-25);
        MainActivity m = (MainActivity) con;
        m.enviarPontoInicial(x, y);
    }

    public void moverPara(int x, int y) {
        caminho.moveTo(x, y-25);
        MainActivity m = (MainActivity) con;
        m.enviarPontoDestino(x, y);
    }

    public void apagar() {
        caminho.reset();
        MainActivity m = (MainActivity) con;
        m.apagarCaminho();
    }

}
