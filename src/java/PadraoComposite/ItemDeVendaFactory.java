package PadraoComposite;

import java.util.HashMap;
import java.util.Map;

public class ItemDeVendaFactory {

    public static ItemDeVenda instanciaItemDeVenda(Integer tipoItem) {
        Map<Integer, String> tipos = new HashMap<>();
        tipos.put(1, "PratoDeEntrada");
        tipos.put(2, "PratoPrincipal");
        tipos.put(3, "Bebida");
        tipos.put(4, "Sobremesa");
        tipos.put(5, "Combo");
        String clazzName = tipos.get(tipoItem);
        ItemDeVenda itemDeVenda = (ItemDeVenda) ItemDeVendaFactory.create(clazzName);
        return itemDeVenda;
    }

    private static ItemDeVenda create(String tipoItemDeVenda) {
        ItemDeVenda tipoItem = null;
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
        tipoItem = (ItemDeVenda) objeto;
        return tipoItem;
    }
}
