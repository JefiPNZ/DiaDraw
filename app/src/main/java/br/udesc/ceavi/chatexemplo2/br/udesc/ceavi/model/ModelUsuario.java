package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.model;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

import java.util.List;
import java.util.Random;

public class ModelUsuario {
    
    public static final String[] CORES_USUARIO = new String[] {
         "red", "blue", "green", "black", "cyan", "magenta", "yellow", "darkgray", "aqua", "fuchsia"
        ,"lime", "maroon", "navy", "olive", "purple", "teal"
    };

    private String nome;
    private int    cor;
    private Path   caminho;

    public ModelUsuario() {
        caminho = new Path();
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
    }

    public void setPontoInicial(float x, float y) {
        caminho.lineTo(x, y);
    }

    public void setPontoLinhaDestino(float x, float y) {
        caminho.moveTo(x, y);
    }

    public void apagarCaminho() {
        caminho.reset();
    }

    public Path getCaminho() {
        return caminho;
    }

    public static int getCorAleatoria(){
        return Color.parseColor(CORES_USUARIO[(int)(Math.random() * (CORES_USUARIO.length - 1))]);
    }
}
