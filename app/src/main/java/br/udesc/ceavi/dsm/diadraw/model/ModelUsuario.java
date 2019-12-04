package br.udesc.ceavi.dsm.diadraw.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.List;
import java.util.Random;

import br.udesc.ceavi.dsm.diadraw.EnumTipoPintura;

public class ModelUsuario {
    
    public static final String[] CORES_USUARIO = new String[] {
         "red", "blue", "green", "black", "cyan", "magenta", "yellow", "darkgray", "aqua", "fuchsia"
        ,"lime", "maroon", "navy", "olive", "purple", "teal"
    };

    private Context oCon;

    private String nome;
    private int    cor;

    private Path   caminho;
    private Paint  pPintura;
    private Paint  pBitmap;

    private Canvas cvCanvas;
    private Bitmap bmBitmap;

    private EnumTipoPintura tipoPintura;

    /*
    * pontos de controle
    */
    private float cX;
    private float cY;

    public ModelUsuario() {
        this(null);
    }

    public ModelUsuario(Context context) {
        oCon    = context;

        caminho = new Path();

        pPintura = new Paint();
        pPintura.setColor(Color.BLACK);
        pPintura.setStyle(Paint.Style.STROKE);
        pPintura.setStrokeWidth(5f);

        pBitmap = new Paint(Paint.DITHER_FLAG);

        WindowManager  wmManager     = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display        dpDisp        = wmManager.getDefaultDisplay();
        DisplayMetrics dpDispMetrics = new DisplayMetrics();

        dpDisp.getMetrics(dpDispMetrics);

        bmBitmap = Bitmap.createBitmap(dpDispMetrics.widthPixels, dpDispMetrics.heightPixels, Bitmap.Config.ARGB_8888);
        cvCanvas = new Canvas(bmBitmap);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
        this.pPintura.setColor(cor);
    }

    public void setPontoInicial(float x, float y) {
        caminho.reset();
        caminho.moveTo(x, y);
        cX = x;
        cY = y;
    }

    public void setPontoLinhaDestino() {
        caminho.lineTo(cX, cY);
        cvCanvas.drawPath(caminho, pPintura);
        caminho.reset();
    }

    /*
     * Necessario fazer dessa forma pois o caminho esta sempre sendo resetado, e so for usado o
     * moveTo e lineTo, ele pega o ponto 0,0 na tela e desenha a linha errada
     */
    public void setPontoMover(float x, float y) {
        caminho.quadTo(cX, cY, (x + cX)/2, ((y) + cY)/2);
        cX = x;
        cY = y;
    }

    public Path getCaminho() {
        return caminho;
    }

    public Paint getPintura() {
        return pPintura;
    }

    public Paint getPinturaBitmap() {
        return pBitmap;
    }

    public Bitmap getBitmap() {
        return bmBitmap;
    }

    public Canvas getCanvas() {
        return cvCanvas;
    }

    public static int getCorAleatoria(){
        return Color.parseColor(CORES_USUARIO[(int)(Math.random() * (CORES_USUARIO.length - 1))]);
    }

    public void setTipoPintura(EnumTipoPintura tipo) {
        switch (tipo) {
            case NORMAL:
                this.tipoPintura = tipo;
                this.pPintura.setXfermode(null);
                this.pPintura.setStrokeWidth(5f);
                break;
            case APAGAR:
                this.tipoPintura = tipo;
                this.pPintura.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                this.pPintura.setStrokeWidth(10f);
                break;
            default:
                this.pPintura.setXfermode(null);
                this.pPintura.setStrokeWidth(5f);
                break;
        }
    }

    public EnumTipoPintura getTipoPintura() {
        if(tipoPintura == null) {
            tipoPintura = EnumTipoPintura.NORMAL;
        }
        return tipoPintura;
    }
}
