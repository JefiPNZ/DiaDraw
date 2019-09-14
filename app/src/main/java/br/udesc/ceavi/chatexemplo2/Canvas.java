package br.udesc.ceavi.chatexemplo2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Canvas extends View {

    private Paint pintura;
    private List<Point> pontos;
    private List<Point> pontoss;
    private Context con;

    public Canvas(Context context) {
        super(context);

        con = context;
        pintura = new Paint();

        pintura.setColor(Color.GREEN);
        pintura.setStyle(Paint.Style.FILL);
        pintura.setStrokeWidth(10);

        pontos = new ArrayList<>();
        pontoss = new ArrayList<>();
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        if(pontos.size() > 0) {

            for(Point p : pontos) {
                canvas.drawPoint(p.x, p.y-50, pintura);
            }
        }


    }

    public static float pxFromDp(final Context context, final float dp) {
//        Log.d("ddsdsdsdsds", "densidade" + context.getResources().getDisplayMetrics().density);
//        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public void setPontos(List<Point> ponto) {
        for(Point p : ponto) {
            pontos.add(p);
        }
//        this.pontos = pontos;
//        this.invalidate();
    }

    public void setPonto(int x, int y) {
        pontos.add(new Point(x, y));
        MainActivity m = (MainActivity) con;
        m.enviarPontos(pontoss);
    }

    public void apagar() {
        this.pontos = new ArrayList<>();
    }

//    public void setX(float x) {
//        this.x = x;
//    }

//    public void setY(float y) {
//        this.y = y;
//    }
}
