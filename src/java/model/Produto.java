package model;

//Ok

public class Produto {
    private Integer produtocod;
    private String nome;
    private Double valor;
    private Integer dificuldade;
    private Integer tipoItem;
    private Integer restaurantecod;
    private Integer ativado;
    private Integer quantidade;

    public Produto() {
    }

    public Integer getProdutocod() {
        return produtocod;
    }

    public Produto setProdutocod(Integer produtocod) {
        this.produtocod = produtocod;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Double getValor() {
        return valor;
    }

    public Produto setValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public Integer getDificuldade() {
        return dificuldade;
    }

    public Produto setDificuldade(Integer dificuldade) {
        this.dificuldade = dificuldade;
        return this;
    }

    public Integer getTipoItem() {
        return tipoItem;
    }

    public Produto setTipoItem(Integer tipoItem) {
        this.tipoItem = tipoItem;
        return this;
    }

    public Integer getRestaurantecod() {
        return restaurantecod;
    }

    public Produto setRestaurantecod(Integer restaurantecod) {
        this.restaurantecod = restaurantecod;
        return this;
    }

    public Integer getAtivado() {
        return ativado;
    }

    public Produto setAtivado(Integer ativado) {
        this.ativado = ativado;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }
    
    
    
}
