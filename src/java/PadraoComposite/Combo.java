package PadraoComposite;

import java.util.ArrayList;
import java.util.List;

public class Combo extends ItemDeVenda {

    List<ItemDeVenda> itens;

    public Combo(List<ItemDeVenda> itens, Integer codigo, String nome, Double valor, Integer dificuldade, Integer restaurantecod, Integer ativado) {
        super(codigo, nome, valor, dificuldade, restaurantecod, ativado);
        this.itens = itens;
    }

    public Combo() {
        this.itens = new ArrayList<>();
    }
    
    @Override
    public void adicionar(ItemDeVenda item) throws Exception {
        this.itens.add(item);
    }

    @Override
    public List<ItemDeVenda> getItens() throws Exception {
        return this.itens;
    }

    @Override
    public ItemDeVenda setItens(List<ItemDeVenda> itens) throws Exception {
        this.itens = itens;
        return this;
    }
    
}
