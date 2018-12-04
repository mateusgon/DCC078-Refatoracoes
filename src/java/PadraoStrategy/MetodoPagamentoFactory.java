package PadraoStrategy;

import java.util.HashMap;
import java.util.Map;

public class MetodoPagamentoFactory {

    public static MetodoPagamento instanciaMetodoPagamento(Integer codigoMetodo) {
        Map<Integer, String> estados = new HashMap<>();
        estados.put(1, "CartaoCredito");
        estados.put(2, "CartaoDebito");
        estados.put(3, "Dinheiro");
        String clazzName = estados.get(codigoMetodo);
        MetodoPagamento estado = (MetodoPagamento) create(clazzName);
        return estado;
    }

    private static MetodoPagamento create(String nomeMetodo) {
        MetodoPagamento metodoObject = null;
        String nomeClasse = "PadraoStrategy.MetodoPagamento" + nomeMetodo;
        Class classe = null;
        Object objeto = null;
        try {
            classe = Class.forName(nomeClasse);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            return null;
        }
        if (!(objeto instanceof MetodoPagamento)) {
            return null;
        }
        metodoObject = (MetodoPagamento) objeto;
        return metodoObject;
    }
}
