package model;

//Ok

public class Mensagem {
    
    private Integer idMensagem;
    private Integer idReceptor;
    private String mensagem;
    
    public Mensagem() {
    }

    public String getMensagem() {
        return mensagem;
    }

    public Mensagem setMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public Integer getIdReceptor() {
        return idReceptor;
    }

    public Mensagem setIdReceptor(Integer idReceptor) {
        this.idReceptor = idReceptor;
        return this;
    }

    public Integer getIdMensagem() {
        return idMensagem;
    }

    public Mensagem setIdMensagem(Integer idMensagem) {
        this.idMensagem = idMensagem;
        return this;
    }
    
}
