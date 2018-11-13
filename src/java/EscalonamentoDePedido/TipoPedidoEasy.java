package EscalonamentoDePedido;

public class TipoPedidoEasy implements TipoPedido {

    private static final TipoPedidoEasy tipoPedidoEasy = new TipoPedidoEasy();

    public static TipoPedidoEasy getTipoPedidoEasy() {
        return tipoPedidoEasy;
    }

    @Override
    public String getTipoPedido() {
        return "Easy";
    }
}
