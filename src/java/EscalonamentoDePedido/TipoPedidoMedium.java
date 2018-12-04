package EscalonamentoDePedido;

public class TipoPedidoMedium implements TipoPedido {

    private static final TipoPedidoMedium tipoPedidoMedium = new TipoPedidoMedium();

    public static TipoPedidoMedium getTipoPedidoMedium() {
        return tipoPedidoMedium;
    }

    @Override
    public String getTipoPedido() {
        return "Medium";
    }

    @Override
    public Integer getIdentificador() {
        return 2;
    }

}
