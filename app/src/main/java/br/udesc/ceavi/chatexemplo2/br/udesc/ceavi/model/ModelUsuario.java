package br.udesc.ceavi.chatexemplo2.br.udesc.ceavi.model;

import android.graphics.Point;

import java.util.List;

public class ModelUsuario {

    private String nome;
    private List<Point> pontos;

    public ModelUsuario() {}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setPontos(List<Point> pontos) {
        this.pontos = pontos;
    }

    public List<Point> getPontos() {
        return pontos;
    }
}
