package PadraoStateObserverMemento;

import java.util.HashMap;
import java.util.Map;

public class EstadoFactory {

    public static PedidoEstado instanciaEstado(Integer codigoEstado) {
        Map<Integer, String> estados = new HashMap<>(); 
        estados.put(1, "Aberto");
        estados.put(2, "Preparar");
        estados.put(3, "Pronto");
        estados.put(4, "Enviar");
        estados.put(5, "Receber");
        String clazzName = estados.get(codigoEstado);
        PedidoEstado estado = (PedidoEstado) EstadoFactory.create(clazzName);
        return estado;
    }

    private static PedidoEstado create(String estado) {
        PedidoEstado estadoObject = null;
        String nomeClasse = "PadraoStateObserverMemento.PedidoEstado" + estado;
        Class classe = null;
        Object objeto = null;
        try {
            classe = Class.forName(nomeClasse);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            return null;
        }
        if (!(objeto instanceof PedidoEstado)) {
            return null;
        }
        estadoObject = (PedidoEstado) objeto;
        return estadoObject;
    }
}
