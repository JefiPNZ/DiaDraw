package br.udesc.ceavi.dsm.diadraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Map;

import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;
import br.udesc.ceavi.dsm.diadraw.model.ModelUsuario;

public class Canvas extends View {

    private Paint pintura;
    private Paint pBitmap;
    private Context con;
    private Conexao conex;

    private Path caminho;
    private Path caminhoApaga;

    public boolean apaga;

    Bitmap bitmap;

    android.graphics.Canvas cv;

    public Canvas(Context context) {
        super(context);

        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        con     = context;
        conex   = Conexao.getInstance();
        pintura = new Paint();

        pintura.setColor(Color.BLACK);
        pintura.setStyle(Paint.Style.STROKE);
        pintura.setStrokeWidth(5f);

        caminho = new Path();
        caminhoApaga = new Path();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();

        disp.getMetrics(metrics);

        pBitmap = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cv = new android.graphics.Canvas(bitmap);
        cv.setBitmap(bitmap);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawColor(Color.argb(255, 255, 0, 0));
//
        canvas.drawBitmap(bitmap, 0, 0, null);

//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint p = new Paint();

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
        p.setAlpha(0xFF);
//            p.setStrokeJoin(Paint.Join.ROUND);
//            p.setStrokeCap(Paint.Cap.ROUND);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10f);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        cv.drawPath(caminhoApaga, p);

        canvas.drawPath(caminho, pintura);

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

    public void linhaPara(float x, float y) {
        if(apaga) {
            caminhoApaga.lineTo(x, y);
        } else {
            caminho.lineTo(x, y-25);
        }
        MainActivity m = (MainActivity) con;
        m.enviarPontoInicial(x, y);
    }

    public void moverPara(float x, float y) {
        if(apaga) {
            caminhoApaga.moveTo(x, y-25);
        } else {

            caminho.moveTo(x, y-25);
        }

        MainActivity m = (MainActivity) con;
        m.enviarPontoDestino(x, y);
    }

    public void apagar() {
        caminho.reset();
        MainActivity m = (MainActivity) con;
        m.apagarCaminho();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moverPara(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                linhaPara(x, y);
                invalidate();
                break;
        }
        return true;
    }
}
