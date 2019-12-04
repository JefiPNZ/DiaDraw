package br.udesc.ceavi.dsm.diadraw;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;

import br.udesc.ceavi.dsm.diadraw.activities.MainActivity;
import br.udesc.ceavi.dsm.diadraw.model.ModelUsuario;

public class ViewCanvas extends View {

    private Context con;
    private Conexao conex;

    public boolean apaga;

    private ModelUsuario usuario;

    public ViewCanvas(Context context, ModelUsuario user) {
        super(context);

        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        this.con     = context;
        this.usuario = user;
        this.conex   = Conexao.getInstance();

        usuario.getPintura().setColor(Color.BLACK);
        usuario.getPintura().setXfermode(null);

    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        switch (usuario.getTipoPintura()) {
            case NORMAL:
                desenhaCaminhoOutrosUsu(canvas);

                canvas.drawBitmap(usuario.getBitmap(), 0, 0, usuario.getPinturaBitmap());
                canvas.drawPath(usuario.getCaminho(), usuario.getPintura());

                break;
            case APAGAR:
                canvas.drawBitmap(usuario.getBitmap(), 0, 0, usuario.getPinturaBitmap());
                canvas.drawPath(usuario.getCaminho(), usuario.getPintura());

                desenhaCaminhoOutrosUsu(canvas);

                break;
        }
//        canvas.drawColor(Color.argb(255, 255, 0, 0));

    }

    private void desenhaCaminhoOutrosUsu(android.graphics.Canvas canvas) {
        if(conex.getUsuarios().size() > 0) {
            Map<String, ModelUsuario> usuarios = conex.getUsuarios();
            for(Map.Entry<String, ModelUsuario> par : usuarios.entrySet()) {
                Path uCaminho = par.getValue().getCaminho();
                if(uCaminho == null) {
                    continue;
                }
                canvas.drawBitmap(par.getValue().getBitmap(), 0, 0, par.getValue().getPinturaBitmap());
                canvas.drawPath(uCaminho, par.getValue().getPintura());
            }
        }
    }

    public void linhaPara() {
        usuario.setPontoLinhaDestino();
        MainActivity m = (MainActivity) con;
        m.enviarPontoDestino();
    }

    public void moverPara(float x, float y) {
        usuario.setPontoInicial(x, y);
        MainActivity m = (MainActivity) con;
        m.enviarPontoInicial(x, y);
    }

    /*
    * Necessario fazer dessa forma pois o caminho esta sempre sendo resetado, e so for usado o
    * moveTo e lineTo, ele pega o ponto 0,0 na tela e desenha a linha errada
    */
    public void mover(float x, float y) {
        usuario.setPontoMover(x, y);
        MainActivity m = (MainActivity) con;
        m.enviarPontoMovimento(x, y);
    }

    public void apagar() {
        EnumTipoPintura tipo = null;
        if(apaga){
            tipo = EnumTipoPintura.APAGAR;
        } else {
            tipo = EnumTipoPintura.NORMAL;
        }
        usuario.setTipoPintura(tipo);
        MainActivity m = (MainActivity) con;
        m.solicitaMudancaPintura(tipo);
    }

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
