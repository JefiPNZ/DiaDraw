package br.udesc.ceavi.dsm.diadraw.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.udesc.ceavi.diadraw.activities.R;

public class ModelAdapterMensagem extends BaseAdapter {

    private Context oContexto;
    private final List<ModelMensagem> mensagens;

    public ModelAdapterMensagem(Context contexto) {
        this.oContexto = contexto;
        this.mensagens = new ArrayList<>();
    }

    public void addMensagem(ModelMensagem mensagem) {
        mensagens.add(mensagem);
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Object getItem(int iPosition) {
        return mensagens.get(iPosition);
    }

    @Override
    public long getItemId(int iPosition) {
        return iPosition;
    }

    @Override
    public View getView(int iPosition, View convertView, ViewGroup parent) {
        LayoutInflater  lInflater   = LayoutInflater.from(oContexto);
        View            vView       = lInflater.inflate(R.layout.adapter_mensagem, parent, false);
        ModelMensagem   oMensagem   = mensagens.get(iPosition);
        TextView        tvRemetente = vView.findViewById(R.id.tvRemetente);
        TextView        tvMsg       = vView.findViewById(R.id.tvMensagem);
//        ImageView       oImg        = vView.findViewById(R.id.imgRemetente);
        LinearLayout    lLayout     = vView.findViewById(R.id.ltLay);

        if(iPosition%2 == 0) {
            lLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }

        tvRemetente.setText(oMensagem.getRemetente());
        tvMsg.setText(oMensagem.getMensagem());
        return vView;
    }
}
