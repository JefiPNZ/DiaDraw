package br.udesc.ceavi.dsm.diadraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Map;

import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;
import br.udesc.ceavi.dsm.diadraw.model.ModelUsuario;

public class ViewCanvas extends View {

    private Context con;
    private Conexao conex;

    private Path caminho;

    public boolean apaga;

    private Paint pintura;
    private Paint pBitmap;

    private Bitmap bitBitmap;
    private Canvas cvCanvas;

    private float cX,
                  cY;// pontos de controle


    public ViewCanvas(Context context) {
        super(context);

        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        con     = context;
        conex   = Conexao.getInstance();
        pintura = new Paint();

        pintura.setColor(Color.BLACK);
        pintura.setStyle(Paint.Style.STROKE);
        pintura.setStrokeWidth(5f);

        caminho = new Path();

        pBitmap = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cvCanvas = new Canvas(bitBitmap);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawColor(Color.argb(255, 255, 0, 0));
//
        canvas.drawBitmap(bitBitmap, 0, 0, pBitmap);

        canvas.drawPath(caminho, pintura);
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(bitmap, 0, 0, null);
//        Paint p = new Paint();

//        if(apaga) {
//            pintura.setAlpha(0xFF);//transperent color
////            pintura.setStyle(Paint.Style.STROKE);
//
////            canvas.clipRect(50, 50 ,50, 50);
////            p.setAntiAlias(true);
//
//            pintura.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//clear the draw
//
//        } else {
//            pintura.setXfermode(null);
//        }
//        p.setAlpha(0xFF);
//            p.setStrokeJoin(Paint.Join.ROUND);
//            p.setStrokeCap(Paint.Cap.ROUND);
//        p.setStyle(Paint.Style.STROKE);
//        p.setStrokeWidth(10f);
//        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        cv.drawPath(caminhoApaga, p);

//        canvas.drawPath(caminho, pintura);

//
//        Paint pinturaUsuario = new Paint();
//        pinturaUsuario.setStyle(Paint.Style.STROKE);
//        pinturaUsuario.setStrokeWidth(5f);
//        if(conex.getUsuarios().size() > 0) {
//            Map<String, ModelUsuario> usuarios = conex.getUsuarios();
//            for(Map.Entry<String, ModelUsuario> par : usuarios.entrySet()) {
//                Path uCaminho = par.getValue().getCaminho();
//                if(uCaminho == null) {
//                    continue;
//                }
//                pinturaUsuario.setColor(par.getValue().getCor());
//                canvas.drawPath(uCaminho, pinturaUsuario);
//            }
//        }
    }


    public static float pxFromDp(final Context context, final float dp) {
//        Log.d("ddsdsdsdsds", "densidade" + context.getResources().getDisplayMetrics().density);
//        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public void linhaPara() {
        caminho.lineTo(cX, cY);
        cvCanvas.drawPath(caminho, pintura);
        caminho.reset();
//        MainActivity m = (MainActivity) con;
//        m.enviarPontoInicial(x, y);
    }

    public void moverPara(float x, float y) {
        caminho.reset();
        caminho.moveTo(x, y);
        cX = x;
        cY = y;
//        MainActivity m = (MainActivity) con;
//        m.enviarPontoDestino(x, y);
    }

    /*
    * Necessario fazer dessa forma pois o caminho esta sempre sendo resetado, e so for usado o
    * moveTo e lineTo, ele pega o ponto 0,0 na tela e desenha a linha errada
    */
    public void mover(float x, float y) {
        float dx = Math.abs(x - cX);
        float dy = Math.abs(y - cY);
        caminho.quadTo(cX, cY, (x + cX)/2, ((y) + cY)/2);
        cX = x;
        cY = y;
    }

    public void apagar() {
        if(apaga){
            pintura.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            pintura.setStrokeWidth(10f);
        } else {
            pintura.setXfermode(null);
            pintura.setStrokeWidth(5f);
        }
//        caminho.reset();
//        MainActivity m = (MainActivity) con;
//        m.apagarCaminho();
    }
//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moverPara(x, y);
//                linhaPara(400, 600);
                invalidate();
                Log.d("teste","Action was DOWN");
                Log.d("X","" + event.getX());
                Log.d("Y","" + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mover(x, y);
                invalidate();
                Log.d("teste","Action was MOVE");
                Log.d("X","" + event.getX());
                Log.d("Y","" + event.getY());
//                Log.d("X con cast","" + (int)event.getX());
//                Log.d("Y com cast","" + (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                linhaPara();
                invalidate();
                Log.d("teste","Action was MOVE");
                Log.d("X","" + event.getX());
                Log.d("Y","" + event.getY());
//                Log.d("X con cast","" + (int)event.getX());
//                Log.d("Y com cast","" + (int)event.getY());
                break;
        }
        return true;
    }
}
