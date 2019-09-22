package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.model;

import android.graphics.Path;
import android.graphics.Point;

import java.util.List;

public class ModelUsuario {

    private String nome;
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
}
