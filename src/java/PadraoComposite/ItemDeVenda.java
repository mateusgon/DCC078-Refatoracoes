package PadraoComposite;

import java.util.List;
import model.Produto;

public abstract class ItemDeVenda {

    protected Integer codigo;
    protected String nome;
    protected Double valor;
    protected Integer dificuldade;
    protected Integer quantidade;
    private Integer restaurantecod;
    private Integer ativado;

    public ItemDeVenda() {
    }

    public ItemDeVenda(Integer codigo, String nome, Double valor, Integer dificuldade, Integer restaurantecod, Integer ativado) {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.dificuldade = dificuldade;
        this.restaurantecod = restaurantecod;
        this.ativado = ativado;
    }

    public void adicionar(ItemDeVenda item) throws Exception {
        throw new Exception("Não é um combo para adicionar");
    }

    public List<ItemDeVenda> getItens() throws Exception {
        throw new Exception("Não é um combo para listar");
    }

    public ItemDeVenda setItens(List<ItemDeVenda> itens) throws Exception {
        throw new Exception("Não é um combo para listar");
    }

    public String getNome() {
        return nome;
    }

    public ItemDeVenda setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Double getValor() {
        return valor;
    }

    public ItemDeVenda setValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public ItemDeVenda setCodigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public Integer getDificuldade() {
        return dificuldade;
    }

    public ItemDeVenda setDificuldade(Integer dificuldade) {
        this.dificuldade = dificuldade;
        return this;
    }

    public Integer getRestaurantecod() {
        return restaurantecod;
    }

    public ItemDeVenda setRestaurantecod(Integer restaurantecod) {
        this.restaurantecod = restaurantecod;
        return this;
    }

    public Integer getAtivado() {
        return ativado;
    }

    public ItemDeVenda setAtivado(Integer ativado) {
        this.ativado = ativado;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ItemDeVenda setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

}
