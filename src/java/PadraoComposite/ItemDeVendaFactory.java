package PadraoComposite;

import java.util.HashMap;
import java.util.Map;
import model.Produto;

public class ItemDeVendaFactory {

    private static ItemDeVenda create(String tipoItemDeVenda) {
        ItemDeVenda itemDeVenda = null;
        String nomeClasse = "PadraoComposite." + tipoItemDeVenda;
        Class classe = null;
        Object objeto = null;
        try {
            classe = Class.forName(nomeClasse);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            return null;
        }
        if (!(objeto instanceof ItemDeVenda)) {
            return null;
        }
        itemDeVenda = (ItemDeVenda) objeto;
        return itemDeVenda;
    }

    public static ItemDeVenda instanciarItemDeVenda(Produto produto) {
        Map<Integer, String> tipos = new HashMap<>();
        tipos.put(1, "PratoDeEntrada");
        tipos.put(2, "PratoPrincipal");
        tipos.put(3, "Bebida");
        tipos.put(4, "Sobremesa");
        tipos.put(5, "Combo");
        String clazzName = tipos.get(produto.getTipoItem());
        ItemDeVenda itemDeVenda = (ItemDeVenda) create(clazzName);
        itemDeVenda.setCodigo(produto.getProdutocod()).setNome(produto.getNome())
                .setValor(produto.getValor()).setDificuldade(produto.getDificuldade())
                .setRestaurantecod(produto.getRestaurantecod())
                .setAtivado(produto.getAtivado()).setQuantidade(produto.getQuantidade());
        return itemDeVenda;
    }
}
