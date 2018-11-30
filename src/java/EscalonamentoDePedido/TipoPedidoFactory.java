package EscalonamentoDePedido;

import PadraoStateObserverMemento.PedidoEstado;
import java.util.HashMap;
import java.util.Map;

public class TipoPedidoFactory {

    public static TipoPedido instanciaTipoPedido(Integer codigoTipoPedido) {
        Map<Integer, String> estados = new HashMap<>();
        estados.put(1, "Easy");
        estados.put(2, "Medium");
        estados.put(3, "Hard");
        String clazzName = estados.get(codigoTipoPedido);
        TipoPedido tipoPedido = (TipoPedido) TipoPedidoFactory.create(clazzName);
        return tipoPedido;
    }

    private static TipoPedido create(String tipoPedido) {
        TipoPedido tipoPedidoObject = null;
        String nomeClasse = "EscalonamentoDePedido.TipoPedido" + tipoPedido;
        Class classe = null;
        Object objeto = null;
        try {
            classe = Class.forName(nomeClasse);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            return null;
        }
        if (!(objeto instanceof TipoPedido)) {
            return null;
        }
        tipoPedidoObject = (TipoPedido) objeto;
        return tipoPedidoObject;
    }
}
