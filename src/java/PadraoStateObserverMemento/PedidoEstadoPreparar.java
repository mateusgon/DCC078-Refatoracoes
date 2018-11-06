package PadraoStateObserverMemento;

import java.util.Observable;

public class PedidoEstadoPreparar extends Observable implements PedidoEstado {

    private String nome;

    public PedidoEstadoPreparar() {
        this.nome = "Preparando";
    }

    @Override
    public void aberto(Pedido a) {

    }

    @Override
    public void preparando(Pedido a) {

    }

    @Override
    public void pronto(Pedido a) {
        a.setEstado(new PedidoEstadoPronto());
    }

    @Override
    public void enviado(Pedido a) {

    }

    @Override
    public void recebido(Pedido a) {

    }

    @Override
    public String getNomeEstado() {
        return this.nome;
    }

}
