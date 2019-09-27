package br.udesc.ceavi.dsm.diadraw.model;

public class ModelMensagem {

    private String remetente;
    private String mensagem;

    public ModelMensagem(String remetente, String mensagem) {
        this.remetente = remetente;
        this.mensagem  = mensagem;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
